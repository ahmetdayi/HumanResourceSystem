package com.obss.hrms.service;

import com.obss.hrms.entity.SecurityHumanResource;
import com.obss.hrms.exception.Constant;
import com.obss.hrms.exception.HumanResourceNotFoundException;
import com.obss.hrms.entity.HumanResource;
import com.obss.hrms.repository.HumanResourceRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.ldap.LdapName;

@Service
@RequiredArgsConstructor
public class HumanResourceService implements UserDetailsService {

    private final HumanResourceRepository humanResourceRepository;



    public HumanResource findNameByDn(Name dn) {
        return humanResourceRepository.findById(dn)
                .orElseThrow(() -> new HumanResourceNotFoundException(Constant.HUMAN_RESOURCE_NOT_FOUND));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HumanResource nameByDn;
        try {
            if (!username.contains("ou"))
            nameByDn = findNameByDn(new LdapName("uid=" + username + ",ou=users"));
            else {
                nameByDn = findNameByDn(new LdapName(username));
            }
        } catch (InvalidNameException e) {
            throw new RuntimeException(e);
        }
        return new SecurityHumanResource(nameByDn);
    }
}
