package com.obss.hrms.entity.elasticsearch;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.obss.hrms.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


import java.time.LocalDate;
import java.util.List;

@Document(indexName = "job_seeker")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobSeekerElastic {
    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String firstname;

    @Field(type = FieldType.Keyword)
    private String lastName;

    private String email;

    @Field(type = FieldType.Date, index = false, store = true, format = DateFormat.basic_date, pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDay;

    @Field(type = FieldType.Keyword)
    private String description;

    private Boolean inBlackList = false;

    private Role role;

    @Field(type = FieldType.Nested)
    private List<PersonalSkillElastic> personalSkillList;

    public JobSeekerElastic(String id, String firstname, String lastName, String email, String description, List<PersonalSkillElastic> personalSkillList) {
        this.id = id;
        this.firstname = firstname;
        this.lastName = lastName;
        this.email = email;
        this.description = description;
        this.personalSkillList = personalSkillList;
    }

    public JobSeekerElastic(String firstname, String lastName, String email, String description) {
        this.firstname = firstname;
        this.lastName = lastName;
        this.email = email;
        this.description = description;
    }

    public JobSeekerElastic( String firstname, String lastName, String email, String description, List<PersonalSkillElastic> personalSkillList) {

        this.firstname = firstname;
        this.lastName = lastName;
        this.email = email;
        this.description = description;
        this.personalSkillList = personalSkillList;
    }
}
