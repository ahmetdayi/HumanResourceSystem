package com.obss.hrms.config;


import com.obss.hrms.security.TokenFilter;
import com.obss.hrms.service.JobSeekerAuthUserService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.DefaultMapOAuth2AccessTokenResponseConverter;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebConfig {
    private final JobSeekerAuthUserService jobSeekerAuthUserService;
    private final TokenFilter tokenFilter;

    private final LogoutHandler logoutHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable).cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(getCorsConfiguration()))
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> {
                            auth.requestMatchers
                                    (
                                    "/advertisement/all",
                                    "/advertisement/findById/**"
                                    ).permitAll();
                            auth.requestMatchers
                                    (
                                            "/advertisement/create",
                                            "/advertisement/update",
                                            "/advertisement/changeStatue/**"
                                    ).hasAuthority("ROLE_ROLE_HR");
                            auth.requestMatchers
                                    (
                                            "/applyAdvertisement/create",
                                            "/applyAdvertisement/byJobSeekerId/**"

                                    ).hasAuthority("USER");
                            auth.requestMatchers
                                    (
                                            "/applyAdvertisement/byAdvertisementId/**",
                                            "/applyAdvertisement/all",
                                            "/applyAdvertisement/changeStatue/**",
                                            "/applyAdvertisement/filter/**",
                                            "/applyAdvertisement/sortedMatch"
                                    ).hasAuthority("ROLE_ROLE_HR");
                            auth.requestMatchers
                                    (
                                            "/jobSeeker/all",
                                            "/jobSeeker/byId"
                                    ).hasAuthority("ROLE_ROLE_HR");
                            auth.requestMatchers
                                    (
                                            "/jobSeeker/update",
                                            "/jobSeeker/addPersonalSkill"
                                    ).hasAuthority("USER");
                            auth.requestMatchers
                                    (
                                            "/blackList/**"
                                    ).hasAuthority("ROLE_ROLE_HR");
                            auth.requestMatchers
                                    (
                                            "/personalSkill/create",
                                            "/personalSkill/delete/**"
                                    ).hasAuthority("ROLE_ROLE_HR");
                            auth.requestMatchers
                                    (
                                            "/personalSkill/all"
                                    ).hasAnyAuthority("USER","ROLE_ROLE_HR");
                            auth.requestMatchers("/profile/oauth2").authenticated();
                            auth.requestMatchers( "/applyAdvertisement/byApplyAdvertisementId/**").authenticated();
                            auth.requestMatchers("/refreshToken").hasAuthority("ROLE_ROLE_HR");
                            auth.requestMatchers("/access").permitAll();
                            auth.requestMatchers("/applyPageSearch/**").hasAuthority("ROLE_ROLE_HR");
                            auth.requestMatchers("/homePageSearch/**").permitAll();
                            auth.anyRequest().permitAll();
                        }
                )
                .oauth2Login(oauth2 ->
                        oauth2.tokenEndpoint(
                                token -> token.accessTokenResponseClient(linkedinTokenResponseClient())
                        )
                ).sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .oauth2Login(oauth2 -> oauth2.userInfoEndpoint(
                        (userInfoEndPoint) -> userInfoEndPoint.userService(jobSeekerAuthUserService)))
                .logout(logout -> {;
                    logout.logoutUrl("/logout");
                    logout.addLogoutHandler(logoutHandler);
                    logout.logoutSuccessHandler((request, response, authentication) -> {
                        SecurityContextHolder.clearContext();
                    });
                })
                .build();
    }


    //    <!---LinkedIn--->
    private static DefaultAuthorizationCodeTokenResponseClient linkedinTokenResponseClient() {
        var defaultMapConverter = new DefaultMapOAuth2AccessTokenResponseConverter();
        Converter<Map<String, Object>, OAuth2AccessTokenResponse> linkedinMapConverter = tokenResponse -> {
            var withTokenType = new HashMap<>(tokenResponse);
            withTokenType.put(OAuth2ParameterNames.TOKEN_TYPE, OAuth2AccessToken.TokenType.BEARER.getValue());
            return defaultMapConverter.convert(withTokenType);
        };

        var httpConverter = new OAuth2AccessTokenResponseHttpMessageConverter();
        httpConverter.setAccessTokenResponseConverter(linkedinMapConverter);

        var restOperations = new RestTemplate(List.of(new FormHttpMessageConverter(), httpConverter));
        restOperations.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
        var client = new DefaultAuthorizationCodeTokenResponseClient();
        client.setRestOperations(restOperations);
        return client;
    }


    //    <!---Swagger--->
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().
                requestMatchers("/swagger-ui/**", "/v3/api-docs/**");
    }


    //    <!---CORS--->
    private CorsConfigurationSource getCorsConfiguration() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(List.of("*"));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return source;
    }


    //    <!---LDAP--->
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.ldapAuthentication().userDnPatterns("uid={0},ou=users,dc=ramhlocal,dc=com")
                .userSearchBase("ou=users,dc=ramhlocal,dc=com")
                .userSearchFilter("uid={0}")
                .groupSearchBase("ou=groups,dc=ramhlocal,dc=com")
                .groupSearchFilter("(&(objectClass=groupOfNames) (member={0}))")
                .contextSource().url("ldap://ldap:389/")
                .managerDn("cn=admin,dc=ramhlocal,dc=com")
                .managerPassword("admin_pass");;
    }

    //    <!---Authentication Manager--->
    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //    <!---ElasticSearch--->
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(RestClient.builder(
                new HttpHost("localhost", 9200, "http") // Elasticsearch sunucu adresini ve bağlantı bilgilerini buraya yazınız.
        ));

    }


}


