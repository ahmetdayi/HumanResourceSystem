package com.obss.hrms.security;


import com.obss.hrms.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    @Value("${spring.security.oauth2.client.provider.linkedin.user-info-uri}")
    private String userMe;

    public UserProfileResponse getUserInfo(String accessToken) {

        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.set("Authorization", "Bearer " + accessToken);

        HttpEntity<Void> userRequestEntity = new HttpEntity<>(userHeaders);

        ResponseEntity<UserProfileResponse> userResponseEntity = new RestTemplate().exchange(userMe, HttpMethod.GET, userRequestEntity, UserProfileResponse.class);
        System.out.println(userResponseEntity);
        return userResponseEntity.getBody();
    }



}
