package com.obss.hrms.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.obss.hrms.request.CreatePersonalSkillRequest;
import com.obss.hrms.response.CreatePersonalSkillResponse;
import com.obss.hrms.service.PersonalSkillService;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonalSkillController.class)
@ContextConfiguration(classes = PersonalSkillController.class)
class PersonalSkillControllerTest {

    @MockBean
    private PersonalSkillService personalSkillService;

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
    @DisplayName("create personal skill when request valid and exist should return createPersonalSkillResponse")
    public void testCreate() throws Exception {
        CreatePersonalSkillRequest request = new CreatePersonalSkillRequest(
                "Java",
                10
        );
        CreatePersonalSkillResponse response = new CreatePersonalSkillResponse(
                "123",
                "Java",
                10
        );
        when(personalSkillService.create(request)).thenReturn(response);

        this.mockMvc.perform(post("/personalSkill/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Java"))
                .andExpect(jsonPath("$.level").value(10));
        verify(personalSkillService).create(request);
    }

    @Test
    @DisplayName("delete personal skill and id exist")
    public void testDelete() throws Exception {
        String id = "23423";
        this.mockMvc.perform(delete("/personalSkill/delete/" + id))
                .andExpect(status().isOk());
    }
}