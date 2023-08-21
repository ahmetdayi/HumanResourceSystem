package com.obss.hrms.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obss.hrms.entity.*;
import com.obss.hrms.exception.Constant;
import com.obss.hrms.exception.HumanResourceNotFoundException;
import com.obss.hrms.repository.JwtTokenRepository;
import com.obss.hrms.request.LoginRequest;
import com.obss.hrms.response.AuthenticationResponse;
import com.obss.hrms.response.LinkedInAccessTokenResponse;
import com.obss.hrms.response.LoginResponse;
import com.obss.hrms.response.UserProfileResponse;
import com.obss.hrms.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final HumanResourceService humanResourceService;

    private final JwtTokenRepository jwtTokenRepository;

    private final JobSeekerService jobSeekerService;
    @Value("${spring.security.oauth2.client.registration.linkedin.client-id}")
    private String client_id;

    @Value("${spring.security.oauth2.client.registration.linkedin.client-secret}")
    private String client_secret;

    @Value("${spring.security.oauth2.client.registration.linkedin.redirect-uri}")
    private String redirect_uri;

    @Value("${spring.security.oauth2.client.registration.linkedin.authorization-grant-type}")
    private String grant_type;

    @Value("${spring.security.oauth2.client.provider.linkedin.token-uri}")
    private String accessUrl;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    private final LdapTemplate ldapTemplate;

    public String getAccessToken(String code) {
        String requestBody = "grant_type=" + grant_type + "&code=" + code + "&redirect_uri=" + redirect_uri +
                "&client_id=" + client_id + "&client_secret=" + client_secret;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<LinkedInAccessTokenResponse> response = restTemplate.exchange(
                "https://www.linkedin.com/oauth/v2/accessToken",
                HttpMethod.POST,
                requestEntity,
                LinkedInAccessTokenResponse.class
        );

        return Objects.requireNonNull(response.getBody()).getAccessToken();
    }

    public LoginResponse login(LoginRequest request) throws InvalidNameException {
        boolean isAuthenticate = isAuthenticateLdap(request);
       if (isAuthenticate) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken
                    (request.username(),
                            request.password());
           authenticationManager.authenticate(token);

            HumanResource humanResource = humanResourceService.findNameByDn(new LdapName("uid=" + request.username() + ",ou=users"));
            SecurityHumanResource securityHumanResource = new SecurityHumanResource(humanResource);

            String jwtToken = jwtUtil.generateToken(securityHumanResource);
            HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                    humanResource.getDn().toString(),
                    humanResource.getFullName(),
                    humanResource.getLastName(),
                    humanResource.getDisplayName()
            );
            var refreshToken = jwtUtil.generateRefreshToken(securityHumanResource);
            revokeAllUserTokens(humanResource);
            saveUserToken(humanResourceEntity, jwtToken);
            AuthenticationResponse build = AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
            return new LoginResponse(
                    build.getAccessToken(),
                    build.getRefreshToken(),
                    humanResourceEntity.getDn(),
                    humanResourceEntity.getFullName(),
                    humanResourceEntity.getLastName(),
                    humanResource.getDisplayName());

        }
       throw new HumanResourceNotFoundException(Constant.HUMAN_RESOURCE_NOT_FOUND);
    }

    private boolean isAuthenticateLdap(LoginRequest request) {
        boolean isAuthenticate = false;
        try {
            // LDAP sunucusunda kimlik doğrulama
            System.out.println(ldapTemplate.authenticate("ou=users", "(uid=" + request.username() + ")", "12345"));
            isAuthenticate = ldapTemplate.authenticate("ou=users", "(uid=" + request.username() + ")", request.password());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAuthenticate;
    }

    private void saveUserToken(HumanResourceEntity humanResource, String jwtToken) {
        var token = JwtToken.builder()
                .humanResource(humanResource)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        jwtTokenRepository.save(token);
    }

    private void revokeAllUserTokens(HumanResource humanResource) {
        SecurityHumanResource securityHumanResource = new SecurityHumanResource(humanResource);
        var validUserTokens = jwtTokenRepository
                .findAllByExpiredAndRevokedAndHumanResource_Dn(
                        false,
                        false,
                        securityHumanResource.getUsername()
                        );
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        jwtTokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws InvalidNameException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userName;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userName = jwtUtil.extractUsername(refreshToken);
        if (userName != null) {
            var humanResource = this.humanResourceService.findNameByDn(new LdapName(userName));
            SecurityHumanResource user = new SecurityHumanResource(humanResource);
            HumanResourceEntity humanResourceEntity = new HumanResourceEntity
                    (
                            humanResource.getDn().toString(),
                            humanResource.getFullName(),
                            humanResource.getLastName(),
                            humanResource.getDisplayName()
                    );

            if (jwtUtil.isTokenValid(refreshToken, user)) {
                var accessToken = jwtUtil.generateToken(user);
                revokeAllUserTokens(humanResource);
                saveUserToken(humanResourceEntity, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
    public UserProfileResponse getUserInfo(String accessToken) {

        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.set("Authorization", "Bearer " + accessToken);

        HttpEntity<Void> userRequestEntity = new HttpEntity<>(userHeaders);

        UserProfileResponse userResponseEntity = new RestTemplate().exchange("https://api.linkedin.com/v2/me", HttpMethod.GET, userRequestEntity, UserProfileResponse.class).getBody();
        String firstName = Objects.requireNonNull(userResponseEntity).getFirstName();
        String lastName = Objects.requireNonNull(userResponseEntity).getLastName();
        String id = Objects.requireNonNull(userResponseEntity).getId();

        System.out.println(firstName + " " + lastName);
        JobSeeker jobSeeker = null;
        try {
            jobSeeker=  jobSeekerService.findById(id);
        }
        catch (Exception e){
            jobSeeker = new JobSeeker(id, firstName, lastName, "", Role.USER);
            jobSeekerService.save(jobSeeker);
        }
        return null;
    }

}
