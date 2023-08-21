package com.obss.hrms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document("jobSeeker")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JobSeeker {
    @Id
    private String id;

    private String firstname;

    private String lastName;

    private String email;

    private LocalDate birthDay;

    private String description;

    private Boolean inBlackList = false;

    private Role role;

    @DBRef
    private List<PersonalSkill> personalSkillList;

    public JobSeeker(String id, String firstname, String lastName, String email, Boolean inBlackList, Role role, List<PersonalSkill> personalSkillList) {
        this.id = id;
        this.firstname = firstname;
        this.lastName = lastName;
        this.email = email;
        this.inBlackList = inBlackList;
        this.role = role;
        this.personalSkillList = personalSkillList;
    }

    public JobSeeker(String id, String firstname, String lastName, String email, Boolean inBlackList, Role role) {
        this.id = id;
        this.firstname = firstname;
        this.lastName = lastName;
        this.email = email;
        this.inBlackList = inBlackList;
        this.role = role;
    }

    public JobSeeker(String id, String firstname, String lastName, String email, Role role) {
        this.id = id;
        this.firstname = firstname;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }

    public JobSeeker(String firstname, String lastName, String email, LocalDate birthDay, String description, Boolean inBlackList, List<PersonalSkill> personalSkillList) {
        this.firstname = firstname;
        this.lastName = lastName;
        this.email = email;
        this.birthDay = birthDay;
        this.description = description;
        this.inBlackList = inBlackList;
        this.personalSkillList = personalSkillList;
    }

    public JobSeeker(String firstname, String lastName, String email, LocalDate birthDay, String description, Boolean inBlackList, Role role, List<PersonalSkill> personalSkillList) {
        this.firstname = firstname;
        this.lastName = lastName;
        this.email = email;
        this.birthDay = birthDay;
        this.description = description;
        this.inBlackList = inBlackList;
        this.role = role;
        this.personalSkillList = personalSkillList;
    }
}
