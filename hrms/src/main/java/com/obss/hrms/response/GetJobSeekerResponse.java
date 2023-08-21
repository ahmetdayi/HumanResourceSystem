package com.obss.hrms.response;

import com.obss.hrms.entity.PersonalSkill;

import java.time.LocalDate;
import java.util.List;

public record GetJobSeekerResponse(
       String id,
       String firstname,
       String lastName,
       String email,
       LocalDate birthDay,
       String description,
       Boolean inBlackList,
       List<PersonalSkill> personalSkillList
) {
}
