package com.obss.hrms.security;

import com.obss.hrms.entity.JobSeeker;
import com.obss.hrms.entity.OAUTH2JobSeeker;
import com.obss.hrms.entity.SecurityHumanResource;
import com.obss.hrms.repository.JwtTokenRepository;
import com.obss.hrms.service.HumanResourceService;
import com.obss.hrms.service.JobSeekerAuthUserService;
import com.obss.hrms.service.JobSeekerService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    private final JobSeekerService jobSeekerService;

    private final HumanResourceService humanResourceService;
    private final TokenProvider tokenProvider;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final JobSeekerAuthUserService jobSeekerAuthUserService;
    private final JwtUtil jwtUtil;

    private final JwtTokenRepository jwtTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getAccessFromRequest(request);

            String username;
            if (StringUtils.hasText(token)) {

                if (!token.startsWith("JWT") && !token.equals("Bearer null")) {
//               OAUTH2
                    System.out.println(token);
                    String userId = tokenProvider.getUserInfo(token).getId();

                    JobSeeker byId = jobSeekerService.findById(userId);

                    OAuth2UserRequest oAuth2UserRequest = createOAuth2UserRequest(token, "linkedin", userId);

                    OAuth2User oAuth2User = jobSeekerAuthUserService.loadUser(oAuth2UserRequest);
                    OAUTH2JobSeeker oauth2JobSeeker = new OAUTH2JobSeeker(oAuth2User, byId);

                    OAuth2AuthenticationToken authentication = new OAuth2AuthenticationToken(oauth2JobSeeker, oauth2JobSeeker.getAuthorities(), "linkedin");
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
//                JWT
                    token = token.substring(3);
                    username = jwtUtil.extractUsername(token);

                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityHumanResource userDetails = (SecurityHumanResource) humanResourceService.loadUserByUsername(username);
                        Boolean isTokenValid = jwtTokenRepository.findByToken(token)
                                .map(t -> !t.isExpired() && !t.isRevoked())
                                .orElse(false);
                        if (jwtUtil.isTokenValid(token, userDetails) && isTokenValid) {
                            UsernamePasswordAuthenticationToken authenticationToken =
                                    new UsernamePasswordAuthenticationToken(
                                            userDetails,
                                            null,
                                            userDetails.getAuthorities()
                                    );
                            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                        }
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
            System.out.println("error " + ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getAccessFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    private OAuth2AuthorizedClient createAuthorizedClient(String accessToken, String registrationId, String userName) {

        OAuth2AccessToken token = new OAuth2AccessToken(
                OAuth2AccessToken.TokenType.BEARER,
                accessToken,
                Instant.now(),
                Instant.now().plus(Duration.ofDays(1))
        );

        OAuth2RefreshToken refreshToken = null;

        return new OAuth2AuthorizedClient(
                clientRegistrationRepository.findByRegistrationId(registrationId),
                userName,
                token,
                refreshToken
        );
    }

    private OAuth2UserRequest createOAuth2UserRequest(String accessToken, String registrationId, String userName) {
        OAuth2AuthorizedClient authorizedClient = createAuthorizedClient(accessToken, registrationId, userName);

        return new OAuth2UserRequest(
                authorizedClient.getClientRegistration(),
                authorizedClient.getAccessToken()
        );
    }


}
