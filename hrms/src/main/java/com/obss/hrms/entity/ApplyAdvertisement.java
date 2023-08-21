package com.obss.hrms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("applyAdvertisement")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ApplyAdvertisement {

    @Id
    private String id;

    private LocalDate applyDate;

    private ApplyAdvertisementStatue applyAdvertisementStatue;

    @DBRef
    private JobSeeker jobSeeker;

    @DBRef
    private Advertisement advertisement;

    public ApplyAdvertisement(
            LocalDate applyDate,
            ApplyAdvertisementStatue applyAdvertisementStatue,
            JobSeeker jobSeeker,
            Advertisement advertisement) {

        this.applyDate = applyDate;
        this.applyAdvertisementStatue = applyAdvertisementStatue;
        this.jobSeeker = jobSeeker;
        this.advertisement = advertisement;
    }
}
