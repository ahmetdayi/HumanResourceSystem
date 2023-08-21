package com.obss.hrms.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateJobSeekerRequest(
        @NotBlank String id,
         String firstname,
         String lastName,
         String birthDay,
         String description
) {
}
