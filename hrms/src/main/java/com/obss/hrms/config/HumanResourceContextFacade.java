package com.obss.hrms.config;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;


@Component
public class HumanResourceContextFacade {


    public HumanResourceContext getUserContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HumanResourceContext humanResourceContext = new HumanResourceContext();
        List<String> roles = new ArrayList<String>();
        for(GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            roles.add(grantedAuthority.getAuthority());
        }
        if(roles.isEmpty()) {
            roles.add("Roles Not Found");
            humanResourceContext.setRoles(roles);
        }else {
            humanResourceContext.setRoles(roles);
        }

        humanResourceContext.setUserName(authentication.getName());

        return humanResourceContext;
    }



}