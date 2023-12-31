package com.obss.hrms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DefaultOauth2UserServiceConfig {
    @Bean
    public DefaultOAuth2UserService defaultOAuth2UserService(){
        return new DefaultOAuth2UserService();
    }
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
