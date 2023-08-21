package com.obss.hrms.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.obss.hrms.entity.*;
import com.obss.hrms.request.CreateApplyAdvertisementRequest;
import com.obss.hrms.response.CreateApplyAdvertisementResponse;
import com.obss.hrms.response.GetApplyAdvertisementResponse;
import com.obss.hrms.service.ApplyAdvertisementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import java.util.List;



import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ApplyAdvertisementController.class)
@ContextConfiguration(classes = ApplyAdvertisementController.class)
class ApplyAdvertisementControllerTest {

    @MockBean
    private ApplyAdvertisementService applyAdvertisementService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("create apply advertisement should return CreateApplyAdvertisementResponse")
    public void testCreate() throws Exception {
        List<PersonalSkill> skills = List.of(new PersonalSkill("345", "Java", 10));
        CreateApplyAdvertisementRequest request = new CreateApplyAdvertisementRequest(
                "ok",
                "123",
                "346"
        );
        JobSeeker jobSeeker = new JobSeeker(
                "1234",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                LocalDate.of(2001, 1, 1),
                "Helloo!",
                false,
                Role.USER,
                skills
        );
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "mc=mike",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        Advertisement advertisement = new Advertisement(
                "2345",
                "980890",
                "obss",
                "java",
                LocalDate.of(2032, 1, 2),
                LocalDate.of(2032, 2, 2),
                AdvertisementStatue.PASSIVE,
                humanResourceEntity,
                skills

        );
        CreateApplyAdvertisementResponse response = new CreateApplyAdvertisementResponse(
                "4765",
                LocalDate.of(2020, 1, 1),
                ApplyAdvertisementStatue.OK,
                jobSeeker,
                advertisement
        );
        when(applyAdvertisementService.create(request)).thenReturn(response);

        this.mockMvc.perform(post("/applyAdvertisement/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.applyDate").value("2020-01-01"))
                .andExpect(jsonPath("$.applyAdvertisementStatue").value("OK"))
                .andExpect(jsonPath("$.jobSeeker.id").value(jobSeeker.getId()))
                .andExpect(jsonPath("$.jobSeeker.firstname").value("Ahmet"))
                .andExpect(jsonPath("$.jobSeeker.lastName").value("Dayı"))
                .andExpect(jsonPath("$.jobSeeker.email").value("ahmet@gmail.com"))
                .andExpect(jsonPath("$.jobSeeker.birthDay").value("2001-01-01"))
                .andExpect(jsonPath("$.jobSeeker.description").value("Helloo!"))
                .andExpect(jsonPath("$.jobSeeker.inBlackList").value(false))
                .andExpect(jsonPath("$.jobSeeker.personalSkillList[0].id").value("345"))
                .andExpect(jsonPath("$.jobSeeker.personalSkillList[0].name").value("Java"))
                .andExpect(jsonPath("$.jobSeeker.personalSkillList[0].level").value(10))
                .andExpect(jsonPath("$.advertisement.id").value(advertisement.getId()))
                .andExpect(jsonPath("$.advertisement.advCode").value(advertisement.getAdvCode()))
                .andExpect(jsonPath("$.advertisement.title").value(advertisement.getTitle()))
                .andExpect(jsonPath("$.advertisement.jobDescription").value(advertisement.getJobDescription()))
                .andExpect(jsonPath("$.advertisement.activationTime").value(advertisement.getActivationTime().toString()))
                .andExpect(jsonPath("$.advertisement.offDate").value(advertisement.getOffDate().toString()))
                .andExpect(jsonPath("$.advertisement.advertisementStatue").value(advertisement.getAdvertisementStatue().toString()))
                .andExpect(jsonPath("$.advertisement.humanResourceEntity.dn").value(humanResourceEntity.getDn()))
                .andExpect(jsonPath("$.advertisement.humanResourceEntity.fullName").value(humanResourceEntity.getFullName()))
                .andExpect(jsonPath("$.advertisement.humanResourceEntity.lastName").value(humanResourceEntity.getLastName()))
                .andExpect(jsonPath("$.advertisement.humanResourceEntity.displayName").value(humanResourceEntity.getDisplayName()))
                .andExpect(jsonPath("$.advertisement.personalSkills[0].id").value("345"))
                .andExpect(jsonPath("$.advertisement.personalSkills[0].name").value("Java"))
                .andExpect(jsonPath("$.advertisement.personalSkills[0].level").value(10));
        verify(applyAdvertisementService).create(request);
    }
    @Test
    @DisplayName("find apply advertisement by job seeker ıd and return GetApplyAdvertisementResponse List ")
    public void testFindByJobSeekerId() throws Exception {
        String jobSeekerId ="4356";
        List<PersonalSkill> skills = List.of(new PersonalSkill("345", "Java", 10));
        JobSeeker jobSeeker = new JobSeeker(
                "1234",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                LocalDate.of(2001, 1, 1),
                "Helloo!",
                false,
                Role.USER,
                skills
        );
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "mc=mike",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        Advertisement advertisement = new Advertisement(
                "2345",
                "980890",
                "obss",
                "java",
                LocalDate.of(2032, 1, 2),
                LocalDate.of(2032, 2, 2),
                AdvertisementStatue.PASSIVE,
                humanResourceEntity,
                skills

        );

        List<GetApplyAdvertisementResponse> response = List.of(new GetApplyAdvertisementResponse(
                "4765",
                LocalDate.of(2020, 1, 1),
                ApplyAdvertisementStatue.OK,
                jobSeeker,
                advertisement
        ));
        when(applyAdvertisementService.findByJobSeekerId(jobSeekerId)).thenReturn(response);
        this.mockMvc.perform(get("/applyAdvertisement/byJobSeekerId/" +jobSeekerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].applyDate").value("2020-01-01"))
                .andExpect(jsonPath("$[0].applyAdvertisementStatue").value("OK"))
                .andExpect(jsonPath("$[0].jobSeeker.id").value(jobSeeker.getId()))
                .andExpect(jsonPath("$[0].jobSeeker.firstname").value("Ahmet"))
                .andExpect(jsonPath("$[0].jobSeeker.lastName").value("Dayı"))
                .andExpect(jsonPath("$[0].jobSeeker.email").value("ahmet@gmail.com"))
                .andExpect(jsonPath("$[0].jobSeeker.birthDay").value("2001-01-01"))
                .andExpect(jsonPath("$[0].jobSeeker.description").value("Helloo!"))
                .andExpect(jsonPath("$[0].jobSeeker.inBlackList").value(false))
                .andExpect(jsonPath("$[0].jobSeeker.personalSkillList[0].id").value("345"))
                .andExpect(jsonPath("$[0].jobSeeker.personalSkillList[0].name").value("Java"))
                .andExpect(jsonPath("$[0].jobSeeker.personalSkillList[0].level").value(10))
                .andExpect(jsonPath("$[0].advertisement.id").value(advertisement.getId()))
                .andExpect(jsonPath("$[0].advertisement.advCode").value(advertisement.getAdvCode()))
                .andExpect(jsonPath("$[0].advertisement.title").value(advertisement.getTitle()))
                .andExpect(jsonPath("$[0].advertisement.jobDescription").value(advertisement.getJobDescription()))
                .andExpect(jsonPath("$[0].advertisement.activationTime").value(advertisement.getActivationTime().toString()))
                .andExpect(jsonPath("$[0].advertisement.offDate").value(advertisement.getOffDate().toString()))
                .andExpect(jsonPath("$[0].advertisement.advertisementStatue").value(advertisement.getAdvertisementStatue().toString()))
                .andExpect(jsonPath("$[0].advertisement.humanResourceEntity.dn").value(humanResourceEntity.getDn()))
                .andExpect(jsonPath("$[0].advertisement.humanResourceEntity.fullName").value(humanResourceEntity.getFullName()))
                .andExpect(jsonPath("$[0].advertisement.humanResourceEntity.lastName").value(humanResourceEntity.getLastName()))
                .andExpect(jsonPath("$[0].advertisement.humanResourceEntity.displayName").value(humanResourceEntity.getDisplayName()))
                .andExpect(jsonPath("$[0].advertisement.personalSkills[0].id").value("345"))
                .andExpect(jsonPath("$[0].advertisement.personalSkills[0].name").value("Java"))
                .andExpect(jsonPath("$[0].advertisement.personalSkills[0].level").value(10));
        verify(applyAdvertisementService).findByJobSeekerId(jobSeekerId);


    }
    @Test
    @DisplayName("find apply advertisement by advertisement ıd and return GetApplyAdvertisementResponse List ")
    public void testFindByAdvertisementId() throws Exception {
        String advertisementId ="4356";
        List<PersonalSkill> skills = List.of(new PersonalSkill("345", "Java", 10));
        JobSeeker jobSeeker = new JobSeeker(
                "1234",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                LocalDate.of(2001, 1, 1),
                "Helloo!",
                false,
                Role.USER,
                skills
        );
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "mc=mike",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        Advertisement advertisement = new Advertisement(
                advertisementId,
                "980890",
                "obss",
                "java",
                LocalDate.of(2032, 1, 2),
                LocalDate.of(2032, 2, 2),
                AdvertisementStatue.PASSIVE,
                humanResourceEntity,
                skills

        );

        List<GetApplyAdvertisementResponse> response = List.of(new GetApplyAdvertisementResponse(
                "4765",
                LocalDate.of(2020, 1, 1),
                ApplyAdvertisementStatue.OK,
                jobSeeker,
                advertisement
        ));
        when(applyAdvertisementService.findByAdvertisementId(advertisementId)).thenReturn(response);
        this.mockMvc.perform(get("/applyAdvertisement/byAdvertisementId/" +advertisementId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].applyDate").value("2020-01-01"))
                .andExpect(jsonPath("$[0].applyAdvertisementStatue").value("OK"))
                .andExpect(jsonPath("$[0].jobSeeker.id").value(jobSeeker.getId()))
                .andExpect(jsonPath("$[0].jobSeeker.firstname").value("Ahmet"))
                .andExpect(jsonPath("$[0].jobSeeker.lastName").value("Dayı"))
                .andExpect(jsonPath("$[0].jobSeeker.email").value("ahmet@gmail.com"))
                .andExpect(jsonPath("$[0].jobSeeker.birthDay").value("2001-01-01"))
                .andExpect(jsonPath("$[0].jobSeeker.description").value("Helloo!"))
                .andExpect(jsonPath("$[0].jobSeeker.inBlackList").value(false))
                .andExpect(jsonPath("$[0].jobSeeker.personalSkillList[0].id").value("345"))
                .andExpect(jsonPath("$[0].jobSeeker.personalSkillList[0].name").value("Java"))
                .andExpect(jsonPath("$[0].jobSeeker.personalSkillList[0].level").value(10))
                .andExpect(jsonPath("$[0].advertisement.id").value(advertisement.getId()))
                .andExpect(jsonPath("$[0].advertisement.advCode").value(advertisement.getAdvCode()))
                .andExpect(jsonPath("$[0].advertisement.title").value(advertisement.getTitle()))
                .andExpect(jsonPath("$[0].advertisement.jobDescription").value(advertisement.getJobDescription()))
                .andExpect(jsonPath("$[0].advertisement.activationTime").value(advertisement.getActivationTime().toString()))
                .andExpect(jsonPath("$[0].advertisement.offDate").value(advertisement.getOffDate().toString()))
                .andExpect(jsonPath("$[0].advertisement.advertisementStatue").value(advertisement.getAdvertisementStatue().toString()))
                .andExpect(jsonPath("$[0].advertisement.humanResourceEntity.dn").value(humanResourceEntity.getDn()))
                .andExpect(jsonPath("$[0].advertisement.humanResourceEntity.fullName").value(humanResourceEntity.getFullName()))
                .andExpect(jsonPath("$[0].advertisement.humanResourceEntity.lastName").value(humanResourceEntity.getLastName()))
                .andExpect(jsonPath("$[0].advertisement.humanResourceEntity.displayName").value(humanResourceEntity.getDisplayName()))
                .andExpect(jsonPath("$[0].advertisement.personalSkills[0].id").value("345"))
                .andExpect(jsonPath("$[0].advertisement.personalSkills[0].name").value("Java"))
                .andExpect(jsonPath("$[0].advertisement.personalSkills[0].level").value(10));
        verify(applyAdvertisementService).findByAdvertisementId(advertisementId);
    }
    @Test
    @DisplayName("find all apply advertisement and return GetApplyAdvertisementResponse List")
    public void testFindAll() throws Exception {
        List<PersonalSkill> skills = List.of(new PersonalSkill("345", "Java", 10));
        JobSeeker jobSeeker = new JobSeeker(
                "1234",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                LocalDate.of(2001, 1, 1),
                "Helloo!",
                false,
                Role.USER,
                skills
        );
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "mc=mike",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        Advertisement advertisement = new Advertisement(
                "2345",
                "980890",
                "obss",
                "java",
                LocalDate.of(2032, 1, 2),
                LocalDate.of(2032, 2, 2),
                AdvertisementStatue.PASSIVE,
                humanResourceEntity,
                skills

        );

        List<GetApplyAdvertisementResponse> response = List.of(new GetApplyAdvertisementResponse(
                "4765",
                LocalDate.of(2020, 1, 1),
                ApplyAdvertisementStatue.OK,
                jobSeeker,
                advertisement
        ));
        when(applyAdvertisementService.findAll()).thenReturn(response);
        this.mockMvc.perform(get("/applyAdvertisement/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].applyDate").value("2020-01-01"))
                .andExpect(jsonPath("$[0].applyAdvertisementStatue").value("OK"))
                .andExpect(jsonPath("$[0].jobSeeker.id").value(jobSeeker.getId()))
                .andExpect(jsonPath("$[0].jobSeeker.firstname").value("Ahmet"))
                .andExpect(jsonPath("$[0].jobSeeker.lastName").value("Dayı"))
                .andExpect(jsonPath("$[0].jobSeeker.email").value("ahmet@gmail.com"))
                .andExpect(jsonPath("$[0].jobSeeker.birthDay").value("2001-01-01"))
                .andExpect(jsonPath("$[0].jobSeeker.description").value("Helloo!"))
                .andExpect(jsonPath("$[0].jobSeeker.inBlackList").value(false))
                .andExpect(jsonPath("$[0].jobSeeker.personalSkillList[0].id").value("345"))
                .andExpect(jsonPath("$[0].jobSeeker.personalSkillList[0].name").value("Java"))
                .andExpect(jsonPath("$[0].jobSeeker.personalSkillList[0].level").value(10))
                .andExpect(jsonPath("$[0].advertisement.id").value(advertisement.getId()))
                .andExpect(jsonPath("$[0].advertisement.advCode").value(advertisement.getAdvCode()))
                .andExpect(jsonPath("$[0].advertisement.title").value(advertisement.getTitle()))
                .andExpect(jsonPath("$[0].advertisement.jobDescription").value(advertisement.getJobDescription()))
                .andExpect(jsonPath("$[0].advertisement.activationTime").value(advertisement.getActivationTime().toString()))
                .andExpect(jsonPath("$[0].advertisement.offDate").value(advertisement.getOffDate().toString()))
                .andExpect(jsonPath("$[0].advertisement.advertisementStatue").value(advertisement.getAdvertisementStatue().toString()))
                .andExpect(jsonPath("$[0].advertisement.humanResourceEntity.dn").value(humanResourceEntity.getDn()))
                .andExpect(jsonPath("$[0].advertisement.humanResourceEntity.fullName").value(humanResourceEntity.getFullName()))
                .andExpect(jsonPath("$[0].advertisement.humanResourceEntity.lastName").value(humanResourceEntity.getLastName()))
                .andExpect(jsonPath("$[0].advertisement.humanResourceEntity.displayName").value(humanResourceEntity.getDisplayName()))
                .andExpect(jsonPath("$[0].advertisement.personalSkills[0].id").value("345"))
                .andExpect(jsonPath("$[0].advertisement.personalSkills[0].name").value("Java"))
                .andExpect(jsonPath("$[0].advertisement.personalSkills[0].level").value(10));
        verify(applyAdvertisementService).findAll();
    }
    @Test
    @DisplayName("update apply advertisement statue")
    public void testUpdateApplyAdvertisementStatue() throws Exception {
        String statue = "ok";
        String applyAdvertisementId = "546546";
        List<PersonalSkill> skills = List.of(new PersonalSkill("345", "Java", 10));
        JobSeeker jobSeeker = new JobSeeker(
                "1234",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                LocalDate.of(2001, 1, 1),
                "Helloo!",
                false,
                Role.USER,
                skills
        );
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "mc=mike",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        Advertisement advertisement = new Advertisement(
                "2345",
                "980890",
                "obss",
                "java",
                LocalDate.of(2032, 1, 2),
                LocalDate.of(2032, 2, 2),
                AdvertisementStatue.PASSIVE,
                humanResourceEntity,
                skills

        );
        ApplyAdvertisement applyAdvertisement = new ApplyAdvertisement(
                applyAdvertisementId,
                LocalDate.of(2020, 1, 1),
                ApplyAdvertisementStatue.OK,
                jobSeeker,
                advertisement
        );



        this.mockMvc.perform(put("/applyAdvertisement/changeStatue/{statue}/{applyAdvertisementId}", statue, applyAdvertisementId))
                .andExpect(status().isCreated());
        verify(applyAdvertisementService).updateApplyAdvertisementStatue(statue,applyAdvertisementId);
    }
    @Test()
    @DisplayName("filter apply advertisement by statue and return GetApplyAdvertisementResponse List")
    public void testFilter() throws Exception {
        String statue = "ok";
        List<PersonalSkill> skills = List.of(new PersonalSkill("345", "Java", 10));
        JobSeeker jobSeeker = new JobSeeker(
                "1234",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                LocalDate.of(2001, 1, 1),
                "Helloo!",
                false,
                Role.USER,
                skills
        );
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "mc=mike",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        Advertisement advertisement = new Advertisement(
                "2345",
                "980890",
                "obss",
                "java",
                LocalDate.of(2032, 1, 2),
                LocalDate.of(2032, 2, 2),
                AdvertisementStatue.PASSIVE,
                humanResourceEntity,
                skills

        );

        List<GetApplyAdvertisementResponse> response = List.of(new GetApplyAdvertisementResponse(
                "4765",
                LocalDate.of(2020, 1, 1),
                ApplyAdvertisementStatue.OK,
                jobSeeker,
                advertisement
        ));
        when(applyAdvertisementService.filter(statue)).thenReturn(response);
        this.mockMvc.perform(get("/applyAdvertisement/filter/{statue}",statue))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].applyDate").value("2020-01-01"))
                .andExpect(jsonPath("$[0].applyAdvertisementStatue").value("OK"))
                .andExpect(jsonPath("$[0].jobSeeker.id").value(jobSeeker.getId()))
                .andExpect(jsonPath("$[0].jobSeeker.firstname").value("Ahmet"))
                .andExpect(jsonPath("$[0].jobSeeker.lastName").value("Dayı"))
                .andExpect(jsonPath("$[0].jobSeeker.email").value("ahmet@gmail.com"))
                .andExpect(jsonPath("$[0].jobSeeker.birthDay").value("2001-01-01"))
                .andExpect(jsonPath("$[0].jobSeeker.description").value("Helloo!"))
                .andExpect(jsonPath("$[0].jobSeeker.inBlackList").value(false))
                .andExpect(jsonPath("$[0].jobSeeker.personalSkillList[0].id").value("345"))
                .andExpect(jsonPath("$[0].jobSeeker.personalSkillList[0].name").value("Java"))
                .andExpect(jsonPath("$[0].jobSeeker.personalSkillList[0].level").value(10))
                .andExpect(jsonPath("$[0].advertisement.id").value(advertisement.getId()))
                .andExpect(jsonPath("$[0].advertisement.advCode").value(advertisement.getAdvCode()))
                .andExpect(jsonPath("$[0].advertisement.title").value(advertisement.getTitle()))
                .andExpect(jsonPath("$[0].advertisement.jobDescription").value(advertisement.getJobDescription()))
                .andExpect(jsonPath("$[0].advertisement.activationTime").value(advertisement.getActivationTime().toString()))
                .andExpect(jsonPath("$[0].advertisement.offDate").value(advertisement.getOffDate().toString()))
                .andExpect(jsonPath("$[0].advertisement.advertisementStatue").value(advertisement.getAdvertisementStatue().toString()))
                .andExpect(jsonPath("$[0].advertisement.humanResourceEntity.dn").value(humanResourceEntity.getDn()))
                .andExpect(jsonPath("$[0].advertisement.humanResourceEntity.fullName").value(humanResourceEntity.getFullName()))
                .andExpect(jsonPath("$[0].advertisement.humanResourceEntity.lastName").value(humanResourceEntity.getLastName()))
                .andExpect(jsonPath("$[0].advertisement.humanResourceEntity.displayName").value(humanResourceEntity.getDisplayName()))
                .andExpect(jsonPath("$[0].advertisement.personalSkills[0].id").value("345"))
                .andExpect(jsonPath("$[0].advertisement.personalSkills[0].name").value("Java"))
                .andExpect(jsonPath("$[0].advertisement.personalSkills[0].level").value(10));
        verify(applyAdvertisementService).filter(statue);
    }
    @Test
    @DisplayName("apply advertisement and job seeker sorted match")
    public void testFindAllApplyAdvertisementsSortedByMatchCount() throws Exception {
        List<PersonalSkill> skills = List.of(new PersonalSkill("345", "Java", 10));
        JobSeeker jobSeeker = new JobSeeker(
                "1234",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                LocalDate.of(2001, 1, 1),
                "Helloo!",
                false,
                Role.USER,
                skills
        );
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "mc=mike",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        Advertisement advertisement = new Advertisement(
                "2345",
                "980890",
                "obss",
                "java",
                LocalDate.of(2032, 1, 2),
                LocalDate.of(2032, 2, 2),
                AdvertisementStatue.PASSIVE,
                humanResourceEntity,
                skills

        );

        List<GetApplyAdvertisementResponse> response = List.of(new GetApplyAdvertisementResponse(
                "4765",
                LocalDate.of(2020, 1, 1),
                ApplyAdvertisementStatue.OK,
                jobSeeker,
                advertisement
        ));
        when(applyAdvertisementService.findAllApplyAdvertisementsSortedByMatchCount()).thenReturn(response);
        this.mockMvc.perform(get("/applyAdvertisement/sortedMatch"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].applyDate").value("2020-01-01"))
                .andExpect(jsonPath("$[0].applyAdvertisementStatue").value("OK"))
                .andExpect(jsonPath("$[0].jobSeeker.id").value(jobSeeker.getId()))
                .andExpect(jsonPath("$[0].jobSeeker.firstname").value("Ahmet"))
                .andExpect(jsonPath("$[0].jobSeeker.lastName").value("Dayı"))
                .andExpect(jsonPath("$[0].jobSeeker.email").value("ahmet@gmail.com"))
                .andExpect(jsonPath("$[0].jobSeeker.birthDay").value("2001-01-01"))
                .andExpect(jsonPath("$[0].jobSeeker.description").value("Helloo!"))
                .andExpect(jsonPath("$[0].jobSeeker.inBlackList").value(false))
                .andExpect(jsonPath("$[0].jobSeeker.personalSkillList[0].id").value("345"))
                .andExpect(jsonPath("$[0].jobSeeker.personalSkillList[0].name").value("Java"))
                .andExpect(jsonPath("$[0].jobSeeker.personalSkillList[0].level").value(10))
                .andExpect(jsonPath("$[0].advertisement.id").value(advertisement.getId()))
                .andExpect(jsonPath("$[0].advertisement.advCode").value(advertisement.getAdvCode()))
                .andExpect(jsonPath("$[0].advertisement.title").value(advertisement.getTitle()))
                .andExpect(jsonPath("$[0].advertisement.jobDescription").value(advertisement.getJobDescription()))
                .andExpect(jsonPath("$[0].advertisement.activationTime").value(advertisement.getActivationTime().toString()))
                .andExpect(jsonPath("$[0].advertisement.offDate").value(advertisement.getOffDate().toString()))
                .andExpect(jsonPath("$[0].advertisement.advertisementStatue").value(advertisement.getAdvertisementStatue().toString()))
                .andExpect(jsonPath("$[0].advertisement.humanResourceEntity.dn").value(humanResourceEntity.getDn()))
                .andExpect(jsonPath("$[0].advertisement.humanResourceEntity.fullName").value(humanResourceEntity.getFullName()))
                .andExpect(jsonPath("$[0].advertisement.humanResourceEntity.lastName").value(humanResourceEntity.getLastName()))
                .andExpect(jsonPath("$[0].advertisement.humanResourceEntity.displayName").value(humanResourceEntity.getDisplayName()))
                .andExpect(jsonPath("$[0].advertisement.personalSkills[0].id").value("345"))
                .andExpect(jsonPath("$[0].advertisement.personalSkills[0].name").value("Java"))
                .andExpect(jsonPath("$[0].advertisement.personalSkills[0].level").value(10));
        verify(applyAdvertisementService).findAllApplyAdvertisementsSortedByMatchCount();

    }


}