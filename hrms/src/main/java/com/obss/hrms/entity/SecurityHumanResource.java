package com.obss.hrms.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class SecurityHumanResource implements UserDetails {
    private final HumanResource humanResource;

    public SecurityHumanResource(HumanResource humanResource) {
        this.humanResource = humanResource;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Role.ROLE_ROLE_HR.name()));
    }

    @Override
    public String getPassword() {
        return humanResource.getUserPassword();
    }

    @Override
    public String getUsername() {
        return humanResource.getDn().toString();
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
}
