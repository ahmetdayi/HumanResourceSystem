package com.obss.hrms.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.obss.hrms.entity.JobSeeker;
import com.obss.hrms.entity.OAUTH2JobSeeker;
import com.obss.hrms.entity.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service

public class JobSeekerAuthUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Value("${linkedin.email-address-uri}")
    private String emailUrl;
    private final DefaultOAuth2UserService defaultOAuth2UserService;

    private final JobSeekerService jobSeekerService;

    private final RestTemplate restTemplate;

    public JobSeekerAuthUserService( DefaultOAuth2UserService defaultOAuth2UserService, JobSeekerService jobSeekerService, RestTemplate restTemplate) {
        this.defaultOAuth2UserService = defaultOAuth2UserService;
        this.jobSeekerService = jobSeekerService;


        this.restTemplate = restTemplate;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User user = defaultOAuth2UserService.loadUser(userRequest);
        String accessToken = userRequest.getAccessToken().getTokenValue();
        return findUserByIdentityNumber(user, accessToken);
    }

    public OAUTH2JobSeeker findUserByIdentityNumber(OAuth2User oAuth2User, String accesstoken) {
        JobSeeker jobSeeker = null;


        String email = getEmail(accesstoken);

        String name = oAuth2User.getName();
        String firstName = Objects.requireNonNull(oAuth2User.getAttribute("localizedFirstName")).toString();
        String lastName = Objects.requireNonNull(oAuth2User.getAttribute("localizedLastName")).toString();
        try {
            jobSeeker = jobSeekerService.findById(name);
            jobSeeker.setEmail(email);
            jobSeekerService.save(jobSeeker);

        } catch (Exception ex) {
            jobSeeker = new JobSeeker(name, firstName, lastName, email, Role.USER);
            jobSeekerService.save(jobSeeker);
        }
        return new OAUTH2JobSeeker(oAuth2User, jobSeeker);
    }

    private String getEmail(String accesstoken) {

        String authorizationHeader = "Bearer " + accesstoken;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationHeader);
        HttpEntity<String> entity = new HttpEntity<>(headers);


        String response = restTemplate.exchange(emailUrl, HttpMethod.GET, entity, String.class).getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode elementsNode = jsonNode.get("elements");

            if (elementsNode != null && elementsNode.isArray() && elementsNode.size() > 0) {

                JsonNode emailAddressNode = elementsNode.get(0).path("handle~").get("emailAddress");

                if (emailAddressNode != null && emailAddressNode.isTextual()) {

                    return emailAddressNode.asText();
                } else {
                    System.out.println("Email Address not found in the response.");
                }
            } else {
                System.out.println("No elements found in the response.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error parsing JSON response.");
        }
        return null;
    }
}
