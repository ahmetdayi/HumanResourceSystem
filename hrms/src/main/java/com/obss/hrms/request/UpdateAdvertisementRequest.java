package com.obss.hrms.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

public record UpdateAdvertisementRequest(
        @NotBlank String id,
         String advCode,
         String title,

         String activationTime,
         String offDate,
         String jobDescription,
         List<String> personalSkillIds

) {
}
