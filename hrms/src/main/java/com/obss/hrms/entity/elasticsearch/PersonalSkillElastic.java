package com.obss.hrms.entity.elasticsearch;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "personal_skill")
@Data
public class PersonalSkillElastic {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String name;

    private Integer level;

    public PersonalSkillElastic(String id, String name, Integer level) {
        this.id = id;
        this.name = name;
        this.level = level;
    }
}
