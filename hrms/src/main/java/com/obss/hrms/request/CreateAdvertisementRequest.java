package com.obss.hrms.request;

import com.obss.hrms.entity.AdvertisementStatue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import javax.naming.ldap.LdapName;
import java.time.LocalDate;
import java.util.List;

public record CreateAdvertisementRequest(
        @NotBlank String advCode,
        @NotBlank String statue,
        @NotBlank String title,
        @NotBlank String activationTime,
        @NotBlank String offDate,
        @NotBlank String jobDescription,
        @NotNull List<String> personalSkillIdList,
        @NotBlank String humanResourceId) {
}
