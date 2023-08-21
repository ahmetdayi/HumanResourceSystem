package com.obss.hrms.repository;

import com.obss.hrms.entity.HumanResource;
import com.obss.hrms.entity.HumanResourceGroup;
import org.springframework.data.ldap.repository.LdapRepository;

public interface HumanResourceGroupRepository extends LdapRepository<HumanResourceGroup> {

}
