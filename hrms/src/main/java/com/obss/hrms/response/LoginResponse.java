package com.obss.hrms.response;


public record LoginResponse(
        String jwtToken,
         String refreshToken,
         String name,
         String fullName,
         String lastName,
         String displayName
) {
}
