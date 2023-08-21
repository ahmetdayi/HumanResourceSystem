package com.obss.hrms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("personalSkill")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PersonalSkill {

    @Id
    private String id;

    private String name;

    private Integer level;

    public PersonalSkill(String name, Integer level) {
        this.name = name;
        this.level = level;
    }
}
