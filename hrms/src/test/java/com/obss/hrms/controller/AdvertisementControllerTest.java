package com.obss.hrms.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.obss.hrms.entity.AdvertisementStatue;
import com.obss.hrms.entity.HumanResourceEntity;
import com.obss.hrms.entity.PersonalSkill;
import com.obss.hrms.request.CreateAdvertisementRequest;
import com.obss.hrms.request.UpdateAdvertisementRequest;
import com.obss.hrms.response.CreateAdvertisementResponse;
import com.obss.hrms.response.GetAdvertisementResponse;
import com.obss.hrms.response.UpdateAdvertisementResponse;
import com.obss.hrms.service.AdvertisementService;
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

@WebMvcTest(AdvertisementController.class)
@ContextConfiguration(classes = AdvertisementController.class)
class AdvertisementControllerTest {
    @MockBean
    private AdvertisementService advertisementService;
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
    @DisplayName("find all advertisement and return GetAdvertisementResponse List")
    public void testFindAll() throws Exception {
        List<PersonalSkill> skills = List.of(new PersonalSkill("345", "Java", 10));
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "mc=mike",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        List<GetAdvertisementResponse> response = List.of(new GetAdvertisementResponse(
                "235",
                "23542",
                "obss",
                "java",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 2, 1),
                AdvertisementStatue.PASSIVE,
                humanResourceEntity,
                skills
        ));
        when(advertisementService.findAll()).thenReturn(response);
        this.mockMvc.perform(get("/advertisement/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("235"))
                .andExpect(jsonPath("$[0].advCode").value("23542"))
                .andExpect(jsonPath("$[0].title").value("obss"))
                .andExpect(jsonPath("$[0].jobDescription").value("java"))
                .andExpect(jsonPath("$[0].activationTime").value("2020-01-01"))
                .andExpect(jsonPath("$[0].offDate").value("2020-02-01"))
                .andExpect(jsonPath("$[0].advertisementStatue").value("PASSIVE"))
                .andExpect(jsonPath("$[0].humanResourceEntity.dn").value(humanResourceEntity.getDn()))
                .andExpect(jsonPath("$[0].humanResourceEntity.fullName").value(humanResourceEntity.getFullName()))
                .andExpect(jsonPath("$[0].humanResourceEntity.lastName").value(humanResourceEntity.getLastName()))
                .andExpect(jsonPath("$[0].humanResourceEntity.displayName").value(humanResourceEntity.getDisplayName()))
                .andExpect(jsonPath("$[0].personalSkills[0].id").value("345"))
                .andExpect(jsonPath("$[0].personalSkills[0].name").value("Java"))
                .andExpect(jsonPath("$[0].personalSkills[0].level").value(10));
        verify(advertisementService).findAll();
    }

    @Test
    @DisplayName("create advertisement and return CreateAdvertisementResponse ")
    public void testCreate() throws Exception {
        CreateAdvertisementRequest request = new CreateAdvertisementRequest(
                "123414",
                "passive",
                "obss",
                "2020-01-01",
                "2020-02-01",
                "java",
                List.of("1323"),
                "cn=mike"
        );
        CreateAdvertisementResponse response = new CreateAdvertisementResponse(
                "456745",
                "123414",
                "obss",
                "java",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 2, 1),
                AdvertisementStatue.PASSIVE
        );
        when(advertisementService.create(request)).thenReturn(response);
        this.mockMvc.perform(post("/advertisement/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.advCode").value("123414"))
                .andExpect(jsonPath("$.title").value("obss"))
                .andExpect(jsonPath("$.jobDescription").value("java"))
                .andExpect(jsonPath("$.activationTime").value("2020-01-01"))
                .andExpect(jsonPath("$.offDate").value("2020-02-01"))
                .andExpect(jsonPath("$.advertisementStatue").value("PASSIVE"));
        verify(advertisementService).create(request);
    }

    @Test
    @DisplayName("change advertisement statue")
    public void testChangeAdvertisementStatue() throws Exception {

        boolean statue = false;
        String advertisementId = "6578568";
        advertisementService.changeAdvertisementStatue(statue, advertisementId);
        this.mockMvc.perform(put("/advertisement/changeStatue/{statue}/{id}", statue, advertisementId))
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("update advertisement and return UpdateAdvertisementResponse")
    public void testUpdate() throws Exception {
        List<PersonalSkill> skills = List.of(new PersonalSkill("345", "Java", 10));
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "mc=mike",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        UpdateAdvertisementRequest request = new UpdateAdvertisementRequest(
                "235",
                "23542",
                "obss",
                "2020-01-01",
                "2020-02-01",
                "java",
                List.of("345")
        );

        UpdateAdvertisementResponse response = new UpdateAdvertisementResponse(
                "235",
                "23542",
                "obss",
                AdvertisementStatue.PASSIVE,
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 2, 1),
                "java",
                skills,
                humanResourceEntity
        );
        when(advertisementService.update(request)).thenReturn(response);
        this.mockMvc.perform(put("/advertisement/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("235"))
                .andExpect(jsonPath("$.advCode").value("23542"))
                .andExpect(jsonPath("$.title").value("obss"))
                .andExpect(jsonPath("$.jobDescription").value("java"))
                .andExpect(jsonPath("$.activationTime").value("2020-01-01"))
                .andExpect(jsonPath("$.offDate").value("2020-02-01"))
                .andExpect(jsonPath("$.advertisementStatue").value("PASSIVE"))
                .andExpect(jsonPath("$.humanResourceEntity.dn").value(humanResourceEntity.getDn()))
                .andExpect(jsonPath("$.humanResourceEntity.fullName").value(humanResourceEntity.getFullName()))
                .andExpect(jsonPath("$.humanResourceEntity.lastName").value(humanResourceEntity.getLastName()))
                .andExpect(jsonPath("$.humanResourceEntity.displayName").value(humanResourceEntity.getDisplayName()))
                .andExpect(jsonPath("$.personalSkills[0].id").value("345"))
                .andExpect(jsonPath("$.personalSkills[0].name").value("Java"))
                .andExpect(jsonPath("$.personalSkills[0].level").value(10));

    }
}