package com.obss.hrms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document("advertisement")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Advertisement {

    @Id
    private String id;

    private String advCode;

    private String title;

    private String jobDescription;

    private LocalDate activationTime;

    private LocalDate offDate;

    private AdvertisementStatue advertisementStatue;

    private HumanResourceEntity humanResourceEntity;

    @DBRef
    private List<PersonalSkill> personalSkills;


}
