package com.obss.hrms.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePersonalSkillRequest(
        @NotBlank String name,
        @NotNull @Max(10) @Min(0) Integer level
) {
}
