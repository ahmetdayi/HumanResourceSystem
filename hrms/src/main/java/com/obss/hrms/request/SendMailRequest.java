package com.obss.hrms.request;

import jakarta.validation.constraints.NotBlank;

public record SendMailRequest(
        @NotBlank String sendTo,
        @NotBlank String message,
        @NotBlank String subject
) {
}
