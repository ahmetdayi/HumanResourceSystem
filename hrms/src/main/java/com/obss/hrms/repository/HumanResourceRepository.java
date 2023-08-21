package com.obss.hrms.repository;

import com.obss.hrms.entity.HumanResource;
import org.springframework.data.ldap.repository.LdapRepository;

import javax.naming.Name;
import java.util.Optional;

public interface HumanResourceRepository extends LdapRepository<HumanResource> {
    Optional<HumanResource> findByDn(Name name);

}
