package com.obss.hrms.response;

public record CreatePersonalSkillResponse(
        String id,
        String name,
        Integer level
) {
}
