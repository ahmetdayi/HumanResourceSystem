package com.obss.hrms.request;

import jakarta.validation.constraints.NotBlank;

public record CreateApplyAdvertisementRequest(

        @NotBlank String applyAdvertisementStatue,
        @NotBlank String jobSeekerId,
        @NotBlank String advertisementId
) {
}
