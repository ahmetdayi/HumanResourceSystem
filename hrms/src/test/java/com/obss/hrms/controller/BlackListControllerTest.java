package com.obss.hrms.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.obss.hrms.entity.HumanResourceEntity;
import com.obss.hrms.entity.JobSeeker;
import com.obss.hrms.entity.PersonalSkill;
import com.obss.hrms.entity.Role;
import com.obss.hrms.request.CreateBlackListRequest;
import com.obss.hrms.response.CreateBlackListResponse;
import com.obss.hrms.response.GetBlackListResponse;
import com.obss.hrms.service.BlackListService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BlackListController.class)
@ContextConfiguration(classes = BlackListController.class)
class BlackListControllerTest {

    @MockBean
    private BlackListService blackListService;

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
    @DisplayName("create black list when request valid and exist should return CreateBlackListResponse")
    public void testCreate() throws Exception {
        CreateBlackListRequest request = new CreateBlackListRequest(
                "Helloo!",
                "123",
                "cn=mike"
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
                List.of(new PersonalSkill("234345", "Java", 10)
                )
        );
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                request.humanResourceId(),
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        CreateBlackListResponse response = new CreateBlackListResponse(
                "456",
                request.description(),
                jobSeeker,
                humanResourceEntity

        );

        when(blackListService.create(request)).thenReturn(response);

        this.mockMvc.perform(post("/blackList/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.description").value("Helloo!"))
                .andExpect(jsonPath("$.jobSeeker.id").value(jobSeeker.getId()))
                .andExpect(jsonPath("$.jobSeeker.firstname").value("Ahmet"))
                .andExpect(jsonPath("$.jobSeeker.lastName").value("Dayı"))
                .andExpect(jsonPath("$.jobSeeker.email").value("ahmet@gmail.com"))
                .andExpect(jsonPath("$.jobSeeker.birthDay").value("2001-01-01"))
                .andExpect(jsonPath("$.jobSeeker.description").value("Helloo!"))
                .andExpect(jsonPath("$.jobSeeker.inBlackList").value(false))
                .andExpect(jsonPath("$.jobSeeker.personalSkillList[0].id").value("234345"))
                .andExpect(jsonPath("$.jobSeeker.personalSkillList[0].name").value("Java"))
                .andExpect(jsonPath("$.jobSeeker.personalSkillList[0].level").value(10))
                .andExpect(jsonPath("$.humanResourceEntity.dn").value(humanResourceEntity.getDn()))
                .andExpect(jsonPath("$.humanResourceEntity.fullName").value(humanResourceEntity.getFullName()))
                .andExpect(jsonPath("$.humanResourceEntity.lastName").value(humanResourceEntity.getLastName()))
                .andExpect(jsonPath("$.humanResourceEntity.displayName").value(humanResourceEntity.getDisplayName()));

        verify(blackListService).create(request);
    }
    @Test
    @DisplayName("find all black list should return GetBlackListResponse list")
    public void testFindAll() throws Exception {
        JobSeeker jobSeeker = new JobSeeker(
                "1234",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                LocalDate.of(2001, 1, 1),
                "Helloo!",
                false,
                Role.USER,
                List.of(new PersonalSkill("234345", "Java", 10)
                )
        );
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "cn=mike",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        List<GetBlackListResponse> response = List.of(new GetBlackListResponse(
                "234234",
                "Helloo!",
                jobSeeker,
                humanResourceEntity
        ));
        when(blackListService.findAll()).thenReturn(response);
        this.mockMvc.perform(get("/blackList/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("234234"))
                .andExpect(jsonPath("$[0].description").value("Helloo!"))
                .andExpect(jsonPath("$[0].jobSeeker.id").value(jobSeeker.getId()))
                .andExpect(jsonPath("$[0].jobSeeker.firstname").value("Ahmet"))
                .andExpect(jsonPath("$[0].jobSeeker.lastName").value("Dayı"))
                .andExpect(jsonPath("$[0].jobSeeker.email").value("ahmet@gmail.com"))
                .andExpect(jsonPath("$[0].jobSeeker.birthDay").value("2001-01-01"))
                .andExpect(jsonPath("$[0].jobSeeker.description").value("Helloo!"))
                .andExpect(jsonPath("$[0].jobSeeker.inBlackList").value(false))
                .andExpect(jsonPath("$[0].jobSeeker.personalSkillList[0].id").value("234345"))
                .andExpect(jsonPath("$[0].jobSeeker.personalSkillList[0].name").value("Java"))
                .andExpect(jsonPath("$[0].jobSeeker.personalSkillList[0].level").value(10))
                .andExpect(jsonPath("$[0].humanResourceEntity.dn").value(humanResourceEntity.getDn()))
                .andExpect(jsonPath("$[0].humanResourceEntity.fullName").value(humanResourceEntity.getFullName()))
                .andExpect(jsonPath("$[0].humanResourceEntity.lastName").value(humanResourceEntity.getLastName()))
                .andExpect(jsonPath("$[0].humanResourceEntity.displayName").value(humanResourceEntity.getDisplayName()));

        verify(blackListService).findAll();
    }
    @Test
    @DisplayName("delete black list and id exist")
    public void testDelete() throws Exception {
        String id = "23423";
        this.mockMvc.perform(delete("/blackList/delete/" + id))
                .andExpect(status().isOk());
    }


}