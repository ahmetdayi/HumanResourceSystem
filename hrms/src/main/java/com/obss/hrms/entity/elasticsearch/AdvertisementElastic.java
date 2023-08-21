package com.obss.hrms.entity.elasticsearch;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.obss.hrms.entity.AdvertisementStatue;
import com.obss.hrms.entity.HumanResourceEntity;

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

@Document(indexName = "advertisement")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementElastic {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String advCode;

    @Field(type = FieldType.Keyword)
    private String title;

    @Field(type = FieldType.Keyword)
    private String jobDescription;
    @Field(type = FieldType.Date, index = false, store = true, format = DateFormat.basic_date, pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate activationTime;
    @Field(type = FieldType.Date, index = false, store = true, format = DateFormat.basic_date, pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate offDate;

    @Field(type = FieldType.Keyword)
    private AdvertisementStatue advertisementStatue;

    private HumanResourceEntity humanResourceEntity;

    @Field(type = FieldType.Nested)
    private List<PersonalSkillElastic> personalSkills;

    public AdvertisementElastic(String advCode, String title, String jobDescription, List<PersonalSkillElastic> personalSkills) {
        this.advCode = advCode;
        this.title = title;
        this.jobDescription = jobDescription;
        this.personalSkills = personalSkills;
    }
}
