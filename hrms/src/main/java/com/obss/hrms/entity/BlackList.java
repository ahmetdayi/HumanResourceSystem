package com.obss.hrms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("blacklist")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BlackList {

    @Id
    private String id;

    private String description;

    @DBRef
    private JobSeeker jobSeeker;

    private HumanResourceEntity humanResourceEntity;

    public BlackList(String description, JobSeeker jobSeeker, HumanResourceEntity humanResourceEntity) {
        this.description = description;
        this.jobSeeker = jobSeeker;
        this.humanResourceEntity = humanResourceEntity;
    }
}
