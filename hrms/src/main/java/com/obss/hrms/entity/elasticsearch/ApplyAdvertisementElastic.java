package com.obss.hrms.entity.elasticsearch;


import com.fasterxml.jackson.annotation.JsonFormat;

import com.obss.hrms.entity.ApplyAdvertisementStatue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


import java.time.LocalDate;

@Document(indexName = "apply_advertisement")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyAdvertisementElastic {

    @Id
    private String id;
    @Field(type = FieldType.Date, index = false, store = true, format = DateFormat.basic_date, pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate applyDate;

    @Field(type = FieldType.Keyword)
    private ApplyAdvertisementStatue applyAdvertisementStatue;

    @Field(type = FieldType.Nested)
    private JobSeekerElastic jobSeeker;

    @Field(type = FieldType.Nested)
    private AdvertisementElastic advertisement;

    public ApplyAdvertisementElastic(JobSeekerElastic jobSeeker, AdvertisementElastic advertisement) {
        this.jobSeeker = jobSeeker;
        this.advertisement = advertisement;
    }
}
