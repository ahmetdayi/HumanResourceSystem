package com.obss.hrms.service.elasticsearch;

import com.obss.hrms.converter.elasticsearch.MongoToElasticConverter;
import com.obss.hrms.entity.Advertisement;
import com.obss.hrms.entity.ApplyAdvertisement;
import com.obss.hrms.entity.JobSeeker;
import com.obss.hrms.entity.PersonalSkill;
import com.obss.hrms.entity.elasticsearch.AdvertisementElastic;
import com.obss.hrms.entity.elasticsearch.ApplyAdvertisementElastic;
import com.obss.hrms.entity.elasticsearch.JobSeekerElastic;
import com.obss.hrms.entity.elasticsearch.PersonalSkillElastic;
import com.obss.hrms.service.AdvertisementService;
import com.obss.hrms.service.ApplyAdvertisementService;
import com.obss.hrms.service.JobSeekerService;
import com.obss.hrms.service.PersonalSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataMigrationService {

    private final PersonalSkillService personalSkillService;

    private final PersonalSkillElasticService personalSkillElasticService;

    private final AdvertisementService advertisementService;

    private final ApplyAdvertisementService applyAdvertisementService;

    private final JobSeekerService jobSeekerService;

    private final ApplyAdvertisementElasticService applyAdvertisementElasticService;

    private final AdvertisementElasticService advertisementElasticService;

    private final JobSeekerElasticService jobSeekerElasticService;

    private final MongoToElasticConverter mongoToElasticConverter;

    public void migrateJobSeekers() {
        List<JobSeeker> jobSeekers = jobSeekerService.findAllJobSeeker();
        List<JobSeekerElastic> jobSeekerElastics = jobSeekerElasticService.findAll();
        List<JobSeeker> filteredJobSeekers = jobSeekers.stream()
                .filter(jobSeeker -> jobSeekerElastics.stream()
                        .noneMatch(jobSeekerElastic -> jobSeeker.getId().equals(jobSeekerElastic.getId()))
                )
                .collect(Collectors.toList());

        jobSeekerElasticService.saveAll(mongoToElasticConverter.convertElasticJS(filteredJobSeekers));


    }
// TODO eklemeden once elastıcı bosalt
    public void migrateAdvertisement() {
        List<Advertisement> advertisements = advertisementService.findAllAdvertisement();
        List<AdvertisementElastic> advertisementElastics = advertisementElasticService.findAll();
        List<Advertisement> filteredAdvertisement = advertisements.stream()
                .filter(advertisement -> advertisementElastics.stream()
                        .noneMatch(advertisementElastic -> advertisement.getId().equals(advertisementElastic.getId()))
                ).toList();
        advertisementElasticService.saveAll(mongoToElasticConverter.convertElasticAD(filteredAdvertisement));
    }

    public void migrateApplyAdvertisement() {
        List<ApplyAdvertisement> applyAdvertisements = applyAdvertisementService.findAllApplyAdvertisement();
        List<ApplyAdvertisementElastic> applyAdvertisementElastics = applyAdvertisementElasticService.findAll();
        List<ApplyAdvertisement> filteredApplyAdvertisement = applyAdvertisements.stream()
                .filter(applyAdvertisement -> applyAdvertisementElastics.stream()
                        .noneMatch(applyAdvertisementElastic -> applyAdvertisement.getId().equals(applyAdvertisementElastic.getId()))
                ).toList();
        applyAdvertisementElasticService.saveAll(mongoToElasticConverter.convertElasticAA(filteredApplyAdvertisement));
    }

    public void migratePersonalSkill() {
        List<PersonalSkill> personalSkills = personalSkillService.findAllPersonalSkill();
        List<PersonalSkillElastic> personalSkillElastics = personalSkillElasticService.findAll();
        List<PersonalSkill> filteredPersonalSkill = personalSkills.stream()
                .filter(personalSkill -> personalSkillElastics.stream()
                        .noneMatch(personalSkillElastic -> personalSkill.getId().equals(personalSkillElastic.getId()))
                ).toList();
        personalSkillElasticService.saveAll(mongoToElasticConverter.convertElasticPS(filteredPersonalSkill));
    }
}
