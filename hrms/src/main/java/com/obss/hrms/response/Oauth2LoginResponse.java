package com.obss.hrms.response;

import com.obss.hrms.entity.JobSeeker;

public record Oauth2LoginResponse(
        String token,
        JobSeeker jobSeeker
) {
}
