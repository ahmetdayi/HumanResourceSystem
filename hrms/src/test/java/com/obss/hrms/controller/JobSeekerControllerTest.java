package com.obss.hrms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obss.hrms.entity.PersonalSkill;
import com.obss.hrms.request.AddPersonalSkillInJobSeekerRequest;
import com.obss.hrms.request.UpdateJobSeekerRequest;
import com.obss.hrms.response.GetJobSeekerResponse;
import com.obss.hrms.response.UpdateJobSeekerResponse;
import com.obss.hrms.service.JobSeekerService;
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


@WebMvcTest(JobSeekerController.class)
@ContextConfiguration(classes = JobSeekerController.class)
class JobSeekerControllerTest {

    @MockBean
    private JobSeekerService jobSeekerService;
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
    @DisplayName("get all job seeker and return getJobSeekerResponse list.")
    public void testFindAll() throws Exception {

        List<GetJobSeekerResponse> response = List.of(new GetJobSeekerResponse(
                "234",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                LocalDate.of(2001, 1, 1),
                "Helloo!",
                false,
                List.of(new PersonalSkill("234345", "Java", 10)
                )));
        when(jobSeekerService.findAll()).thenReturn(response);

        this.mockMvc.perform(get("/jobSeeker/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("234"))
                .andExpect(jsonPath("$[0].firstname").value("Ahmet"))
                .andExpect(jsonPath("$[0].lastName").value("Dayı"))
                .andExpect(jsonPath("$[0].email").value("ahmet@gmail.com"))
                .andExpect(jsonPath("$[0].birthDay").value("2001-01-01"))
                .andExpect(jsonPath("$[0].description").value("Helloo!"))
                .andExpect(jsonPath("$[0].inBlackList").value(false))
                .andExpect(jsonPath("$[0].personalSkillList[0].id").value("234345"))
                .andExpect(jsonPath("$[0].personalSkillList[0].name").value("Java"))
                .andExpect(jsonPath("$[0].personalSkillList[0].level").value(10));

        verify(jobSeekerService).findAll();
    }

    @Test
    @DisplayName("find job seeker by job seeker id and id exist should return getJobSeekerResponse")
    public void findByJobSeekerId() throws Exception {
        String id = "435234";
        GetJobSeekerResponse response = new GetJobSeekerResponse(
                id,
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                LocalDate.of(2001, 1, 1),
                "Helloo!",
                false,
                List.of(new PersonalSkill("234345", "Java", 10)
                ));
        when(jobSeekerService.findJobSeekerById(id)).thenReturn(response);
        this.mockMvc.perform(get("/jobSeeker/byId/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.firstname").value("Ahmet"))
                .andExpect(jsonPath("$.lastName").value("Dayı"))
                .andExpect(jsonPath("$.email").value("ahmet@gmail.com"))
                .andExpect(jsonPath("$.birthDay").value("2001-01-01"))
                .andExpect(jsonPath("$.description").value("Helloo!"))
                .andExpect(jsonPath("$.inBlackList").value(false))
                .andExpect(jsonPath("$.personalSkillList[0].id").value("234345"))
                .andExpect(jsonPath("$.personalSkillList[0].name").value("Java"))
                .andExpect(jsonPath("$.personalSkillList[0].level").value(10));

        verify(jobSeekerService).findJobSeekerById(id);
    }

    @Test
    @DisplayName("update jobSeeker should return updateJobSeekerResponse")
    public void testUpdate() throws Exception {
        String id = "23423";
        UpdateJobSeekerRequest request = new UpdateJobSeekerRequest(
                id,
                "Ahmet",
                "Dayı",
                "2001-01-01",
                "Helloo!"
        );
        UpdateJobSeekerResponse response = new UpdateJobSeekerResponse(
                id,
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                LocalDate.of(2001, 1, 1),
                "Helloo!",
                false,
                List.of(new PersonalSkill("234345", "Java", 10)
                )
        );
        when(jobSeekerService.update(request)).thenReturn(response);
        this.mockMvc.perform(put("/jobSeeker/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.firstname").value("Ahmet"))
                .andExpect(jsonPath("$.lastName").value("Dayı"))
                .andExpect(jsonPath("$.birthDay").value("2001-01-01"))
                .andExpect(jsonPath("$.description").value("Helloo!"));
        verify(jobSeekerService).update(request);
    }
    @Test
    @DisplayName("add personal skill in job seeker should return getJobSeekerResponse")
    public void testAddPersonalSkill() throws Exception {
        String jobSeekerId = "23423";
        List<PersonalSkill> skills = List.of(new PersonalSkill("345","Java",10));
        List<String> skillIdList = List.of("345");
        AddPersonalSkillInJobSeekerRequest request = new AddPersonalSkillInJobSeekerRequest(
                jobSeekerId,
                skillIdList
        );
        GetJobSeekerResponse response = new GetJobSeekerResponse(
                jobSeekerId,
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                LocalDate.of(2001, 1, 1),
                "Helloo!",
                false,
                skills
        );
        when(jobSeekerService.addPersonalSkill(request)).thenReturn(response);
        this.mockMvc.perform(put("/jobSeeker/addPersonalSkill")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(jobSeekerId))
                .andExpect(jsonPath("$.firstname").value("Ahmet"))
                .andExpect(jsonPath("$.lastName").value("Dayı"))
                .andExpect(jsonPath("$.email").value("ahmet@gmail.com"))
                .andExpect(jsonPath("$.birthDay").value("2001-01-01"))
                .andExpect(jsonPath("$.description").value("Helloo!"))
                .andExpect(jsonPath("$.inBlackList").value(false))
                .andExpect(jsonPath("$.personalSkillList[0].id").value("345"))
                .andExpect(jsonPath("$.personalSkillList[0].name").value("Java"))
                .andExpect(jsonPath("$.personalSkillList[0].level").value(10));
        verify(jobSeekerService).addPersonalSkill(request);
    }

}