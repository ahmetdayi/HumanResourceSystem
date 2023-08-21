package com.obss.hrms.request;

import jakarta.validation.constraints.NotBlank;

import javax.naming.ldap.LdapName;

public record CreateBlackListRequest(
        @NotBlank String description,
        @NotBlank String jobSeekerId,
        @NotBlank String humanResourceId
) {
}
