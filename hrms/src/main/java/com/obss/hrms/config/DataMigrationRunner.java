package com.obss.hrms.config;

import com.obss.hrms.service.elasticsearch.DataMigrationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(Integer.MIN_VALUE)
public class DataMigrationRunner {

    private final DataMigrationService dataMigrationService;


    @Scheduled(fixedRateString = "PT30S")
    @PostConstruct
    public void setDataMigration() {
        dataMigrationService.migrateAdvertisement();
        dataMigrationService.migrateJobSeekers();
        dataMigrationService.migrateApplyAdvertisement();
        dataMigrationService.migratePersonalSkill();
    }
}
