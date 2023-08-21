package com.obss.hrms.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class OAUTH2JobSeeker implements OAuth2User, UserDetails {

    private OAuth2User oAuth2User;
    private final JobSeeker jobSeeker;

    public OAUTH2JobSeeker(OAuth2User oAuth2User, JobSeeker jobSeeker) {
        this.oAuth2User = oAuth2User;
        this.jobSeeker = jobSeeker;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return jobSeeker.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(jobSeeker.getRole().name()));
    }

    @Override
    public String getName() {
        return jobSeeker.getId();
    }
}