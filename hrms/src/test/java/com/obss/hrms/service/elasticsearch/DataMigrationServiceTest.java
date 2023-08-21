package com.obss.hrms.service.elasticsearch;

import com.obss.hrms.converter.elasticsearch.MongoToElasticConverter;
import com.obss.hrms.entity.*;
import com.obss.hrms.entity.elasticsearch.AdvertisementElastic;
import com.obss.hrms.entity.elasticsearch.ApplyAdvertisementElastic;
import com.obss.hrms.entity.elasticsearch.JobSeekerElastic;
import com.obss.hrms.entity.elasticsearch.PersonalSkillElastic;
import com.obss.hrms.service.AdvertisementService;
import com.obss.hrms.service.ApplyAdvertisementService;
import com.obss.hrms.service.JobSeekerService;
import com.obss.hrms.service.PersonalSkillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;


import static org.mockito.Mockito.*;

class DataMigrationServiceTest {


    private PersonalSkillService personalSkillService;

    private PersonalSkillElasticService personalSkillElasticService;

    private AdvertisementService advertisementService;

    private ApplyAdvertisementService applyAdvertisementService;

    private JobSeekerService jobSeekerService;

    private ApplyAdvertisementElasticService applyAdvertisementElasticService;

    private AdvertisementElasticService advertisementElasticService;

    private JobSeekerElasticService jobSeekerElasticService;

    private MongoToElasticConverter mongoToElasticConverter;

    private DataMigrationService dataMigrationService;

    @BeforeEach
    void setUp() {
        personalSkillService = mock(PersonalSkillService.class);
        personalSkillElasticService = mock(PersonalSkillElasticService.class);
        advertisementService = mock(AdvertisementService.class);
        applyAdvertisementService = mock(ApplyAdvertisementService.class);
        jobSeekerService = mock(JobSeekerService.class);
        applyAdvertisementElasticService = mock(ApplyAdvertisementElasticService.class);
        advertisementElasticService = mock(AdvertisementElasticService.class);
        jobSeekerElasticService = mock(JobSeekerElasticService.class);
        mongoToElasticConverter = mock(MongoToElasticConverter.class);
        dataMigrationService = new DataMigrationService(
                personalSkillService,
                personalSkillElasticService,
                advertisementService,
                applyAdvertisementService,
                jobSeekerService,
                applyAdvertisementElasticService,
                advertisementElasticService,
                jobSeekerElasticService,
                mongoToElasticConverter
        );
    }

    @Test
    @DisplayName("migrate jobSeeker and jobSeeker elastic")
    public void testMigrateJobSeekers() {
        List<JobSeeker> jobSeeker = List.of(new JobSeeker(
                "234",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                false,
                null
        ));
        List<JobSeekerElastic> jobSeekerElastics = List.of(new JobSeekerElastic(
                "1214345",
                "ahmet",
                "Dayı",
                "ahmet@gmail.com",
                List.of(new PersonalSkillElastic("123123", "java", 10))
        ));
        List<JobSeekerElastic> convertedJobSeeker = List.of(new JobSeekerElastic(
                "234",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                null,
                null,
                false,
                null,
                null
        ));
        when(jobSeekerService.findAllJobSeeker()).thenReturn(jobSeeker);
        when(jobSeekerElasticService.findAll()).thenReturn(jobSeekerElastics);
        when(mongoToElasticConverter.convertElasticJS(jobSeeker)).thenReturn(convertedJobSeeker);
        dataMigrationService.migrateJobSeekers();

        verify(jobSeekerService).findAllJobSeeker();
        verify(jobSeekerElasticService).findAll();
        verify(jobSeekerElasticService).saveAll(convertedJobSeeker);
    }

    @Test
    @DisplayName("migrate advertisement and advertisement elastic")
    public void testMigrateAdvertisement(){
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "cn=mike",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        List<Advertisement> advertisements = List.of(new Advertisement(
                "1565",
                "124324",
                "Obss",
                "java",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2025, 1, 1),
                AdvertisementStatue.PASSIVE,
                humanResourceEntity,
                null
        ));
        List<AdvertisementElastic> advertisementElastics = List.of(new AdvertisementElastic(
                "156545",
                "124324",
                "Obss",
                "java",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2025, 1, 1),
                AdvertisementStatue.PASSIVE,
                humanResourceEntity,
                null
        ));
        List<AdvertisementElastic> convertedAdvertisements = List.of(new AdvertisementElastic(
                "1565",
                "124324",
                "Obss",
                "java",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2025, 1, 1),
                AdvertisementStatue.PASSIVE,
                humanResourceEntity,
                null
        ));

        when(advertisementService.findAllAdvertisement()).thenReturn(advertisements);
        when(advertisementElasticService.findAll()).thenReturn(advertisementElastics);
        when(mongoToElasticConverter.convertElasticAD(advertisements)).thenReturn(convertedAdvertisements);

        dataMigrationService.migrateAdvertisement();

        verify(advertisementService).findAllAdvertisement();
        verify(advertisementElasticService).findAll();
        verify(mongoToElasticConverter).convertElasticAD(advertisements);
        verify(advertisementElasticService).saveAll(convertedAdvertisements);
    }

    @Test
    @DisplayName("migrate apply advertisement and apply advertisement elastic")
    public void testMigrateApplyAdvertisement(){
        JobSeeker jobSeeker = new JobSeeker(
                "8908",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                false,
                Role.USER,
                null
        );
        JobSeekerElastic jobSeekerElastic = new JobSeekerElastic(
                "8908",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                null,
                null,
                false,
                Role.USER,
                null
        );
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "cn=mike",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        ApplyAdvertisement advertisement = new ApplyAdvertisement(
                "89089",
                LocalDate.of(2001, 1, 1),
                ApplyAdvertisementStatue.PROCESSING,
                jobSeeker,
                new Advertisement(
                        "90567",
                        "124324",
                        "Obss",
                        "java",
                        LocalDate.of(2024, 1, 1),
                        LocalDate.of(2025, 1, 1),
                        AdvertisementStatue.PASSIVE,
                        humanResourceEntity,
                        null
                ));
        ApplyAdvertisementElastic convertedApplyAdvertisement = new ApplyAdvertisementElastic(
                "89089",
                LocalDate.of(2001, 1, 1),
                ApplyAdvertisementStatue.PROCESSING,
                jobSeekerElastic,
                new AdvertisementElastic(
                        "90567",
                        "124324",
                        "Obss",
                        "java",
                        LocalDate.of(2024, 1, 1),
                        LocalDate.of(2025, 1, 1),
                        AdvertisementStatue.PASSIVE,
                        humanResourceEntity,
                        null
                ));
        List<ApplyAdvertisement> applyAdvertisementList = List.of(advertisement);
        List<ApplyAdvertisementElastic> convertedApplyAdvertisementList = List.of(convertedApplyAdvertisement);
        ApplyAdvertisementElastic applyAdvertisementElastic = new ApplyAdvertisementElastic(
                "8908912",
                LocalDate.of(2001, 1, 1),
                ApplyAdvertisementStatue.PROCESSING,
                jobSeekerElastic,
                new AdvertisementElastic(
                        "90567",
                        "124324",
                        "Obss",
                        "java",
                        LocalDate.of(2024, 1, 1),
                        LocalDate.of(2025, 1, 1),
                        AdvertisementStatue.PASSIVE,
                        humanResourceEntity,
                        null
                ));
        List<ApplyAdvertisementElastic> applyAdvertisementElasticList = List.of(applyAdvertisementElastic);

        when(applyAdvertisementService.findAllApplyAdvertisement()).thenReturn(applyAdvertisementList);
        when(applyAdvertisementElasticService.findAll()).thenReturn(applyAdvertisementElasticList);
        when(mongoToElasticConverter.convertElasticAA(applyAdvertisementList)).thenReturn(convertedApplyAdvertisementList);

        dataMigrationService.migrateApplyAdvertisement();

        verify(applyAdvertisementService).findAllApplyAdvertisement();
        verify(applyAdvertisementElasticService).findAll();
        verify(mongoToElasticConverter).convertElasticAA(applyAdvertisementList);
        verify(applyAdvertisementElasticService).saveAll(convertedApplyAdvertisementList);
    }

    @Test
    @DisplayName("migrate personal skill and personal skill elastic")
    public void testMigratePersonalSkill(){
        List<PersonalSkill> personalSkills = List.of(new PersonalSkill(
                "1123",
                "java",
                10)
        );
        List<PersonalSkillElastic> convertedPersonalSkills = List.of(new PersonalSkillElastic(
                "1123",
                "java",
                10)
        );
        List<PersonalSkillElastic> personalSkillElastics = List.of(new PersonalSkillElastic(
                "123",
                "java",
                10
        ));
        when(personalSkillService.findAllPersonalSkill()).thenReturn(personalSkills);
        when(personalSkillElasticService.findAll()).thenReturn(personalSkillElastics);
        when(mongoToElasticConverter.convertElasticPS(personalSkills)).thenReturn(convertedPersonalSkills);

        dataMigrationService.migratePersonalSkill();

        verify(personalSkillService).findAllPersonalSkill();
        verify(personalSkillElasticService).findAll();
        verify(mongoToElasticConverter).convertElasticPS(personalSkills);
        verify(personalSkillElasticService).saveAll(convertedPersonalSkills);
    }

}