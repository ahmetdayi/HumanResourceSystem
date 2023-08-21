package com.obss.hrms.service.elasticsearch;

import com.obss.hrms.converter.elasticsearch.ApplyAdvertisementElasticConverter;
import com.obss.hrms.entity.*;
import com.obss.hrms.entity.elasticsearch.AdvertisementElastic;
import com.obss.hrms.entity.elasticsearch.ApplyAdvertisementElastic;
import com.obss.hrms.entity.elasticsearch.JobSeekerElastic;
import com.obss.hrms.repository.elasticsearch.ApplyAdvertisementElasticRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplyAdvertisementElasticServiceTest {

    private ApplyAdvertisementElasticRepository applyAdvertisementElasticRepository;

    private ApplyAdvertisementElasticConverter applyAdvertisementElasticConverter;

    private ApplyAdvertisementElasticService applyAdvertisementElasticService;

    @BeforeEach
    void setUp() {
        applyAdvertisementElasticRepository = mock(ApplyAdvertisementElasticRepository.class);
        applyAdvertisementElasticConverter = mock(ApplyAdvertisementElasticConverter.class);
        applyAdvertisementElasticService = new ApplyAdvertisementElasticService(
                applyAdvertisementElasticRepository,
                applyAdvertisementElasticConverter);

    }
    @Test
    @DisplayName("search all apply advertisement fields and return GetApplyAdvertisementResponse list")
    public void testSearchApplyAdvertisement(){
        String query = "ahmet";
        JobSeeker jobSeeker = new JobSeeker(
                "8908",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                false,
                Role.USER,
                List.of(new PersonalSkill("1232", "java", 5))
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
                        List.of(new PersonalSkill("123456", "java", 10))
                ));
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
        List<ApplyAdvertisement> applyAdvertisementList = List.of(advertisement);
        List<ApplyAdvertisementElastic> expected = List.of(applyAdvertisementElastic);

        when(applyAdvertisementElasticRepository
                .findByJobSeeker_FirstnameLikeOrJobSeeker_LastNameLikeOrJobSeeker_DescriptionLikeOrJobSeeker_PersonalSkillList_nameLikeOrAdvertisement_AdvCodeLikeOrAdvertisement_TitleLikeOrAdvertisement_JobDescriptionLikeOrAdvertisement_PersonalSkills_nameLike(query,query,query,query,query,query,query,query)).thenReturn(expected);


        List<ApplyAdvertisementElastic> actual = applyAdvertisementElasticService.searchApplyAdvertisement(query);

        assertEquals(expected,actual);

        verify(applyAdvertisementElasticRepository).findByJobSeeker_FirstnameLikeOrJobSeeker_LastNameLikeOrJobSeeker_DescriptionLikeOrJobSeeker_PersonalSkillList_nameLikeOrAdvertisement_AdvCodeLikeOrAdvertisement_TitleLikeOrAdvertisement_JobDescriptionLikeOrAdvertisement_PersonalSkills_nameLike(query,query,query,query,query,query,query,query);

    }
    @Test
    @DisplayName("save all apply advertisement elastic and return ApplyAdvertisementElastic list")
    public void testSaveAll(){
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
        List<ApplyAdvertisementElastic> applyAdvertisementElastics = List.of(applyAdvertisementElastic);
        applyAdvertisementElasticService.saveAll(applyAdvertisementElastics);

        verify(applyAdvertisementElasticRepository).saveAll(applyAdvertisementElastics);
    }
    @Test
    @DisplayName("find all apply advertisement elastic and return ApplyAdvertisementElastic list")
    public void testFindAll(){
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
        List<ApplyAdvertisementElastic> expected = List.of(applyAdvertisementElastic);

        when(applyAdvertisementElasticRepository.findAll()).thenReturn(expected);

        List<ApplyAdvertisementElastic> actual = applyAdvertisementElasticService.findAll();

        assertEquals(expected,actual);

        verify(applyAdvertisementElasticRepository).findAll();

    }
}