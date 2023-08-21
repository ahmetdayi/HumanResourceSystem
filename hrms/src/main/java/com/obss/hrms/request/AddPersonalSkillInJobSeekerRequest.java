package com.obss.hrms.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AddPersonalSkillInJobSeekerRequest(
        @NotBlank String jobSeekerId,
        @NotNull List<String> personalSkillIdList
) {
}
