package com.obss.hrms.service.elasticsearch;

import com.obss.hrms.converter.elasticsearch.JobSeekerElasticConverter;

import com.obss.hrms.entity.elasticsearch.JobSeekerElastic;
import com.obss.hrms.entity.elasticsearch.PersonalSkillElastic;
import com.obss.hrms.repository.elasticsearch.JobSeekerElasticRepository;
import com.obss.hrms.response.GetJobSeekerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JobSeekerElasticServiceTest {
    private  JobSeekerElasticRepository jobSeekerElasticRepository;

    private  JobSeekerElasticConverter jobSeekerElasticConverter;

    private JobSeekerElasticService jobSeekerElasticService;

    @BeforeEach
    void setUp() {
        jobSeekerElasticRepository = mock(JobSeekerElasticRepository.class);
        jobSeekerElasticConverter = mock(JobSeekerElasticConverter.class);
        jobSeekerElasticService = new JobSeekerElasticService(
                jobSeekerElasticRepository,
                jobSeekerElasticConverter);
    }

    @Test
    @DisplayName("Save all jobSeeker")
    public void SaveAll(){
        List<JobSeekerElastic> jobSeekerElastic = List.of(new JobSeekerElastic(
                "1214345",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                List.of(new PersonalSkillElastic("123123","java",10))
        ));

        jobSeekerElasticService.saveAll(jobSeekerElastic);
        verify(jobSeekerElasticRepository).saveAll(jobSeekerElastic);
    }
    @Test
    @DisplayName("search jobseeker's variable")
    public void testSearch(){
        String query = "ahmet";
        List<JobSeekerElastic> jobSeekerElastic = List.of(new JobSeekerElastic(
                "1214345",
                "ahmet",
                "Dayı",
                "ahmet@gmail.com",
                List.of(new PersonalSkillElastic("123123","java",10))
        ));
        List<GetJobSeekerResponse> expected = List.of(new GetJobSeekerResponse(
                "1214345",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                LocalDate.of(2003, 3, 3),
                "I am Ahmet",
                false,
                null
        ));
        when(jobSeekerElasticRepository.findByFirstnameLikeOrLastNameLikeOrDescriptionLikeOrPersonalSkillList_nameLike(query,query,query,query)).thenReturn(jobSeekerElastic);
        when(jobSeekerElasticConverter.convert(jobSeekerElastic)).thenReturn(expected);

        List<GetJobSeekerResponse> actual = jobSeekerElasticService.search(query);

        assertEquals(expected,actual);

        verify(jobSeekerElasticRepository).findByFirstnameLikeOrLastNameLikeOrDescriptionLikeOrPersonalSkillList_nameLike(query,query,query,query);
        verify(jobSeekerElasticConverter).convert(jobSeekerElastic);

    }
    @Test
    @DisplayName("find all JobSeekerElastic and return JobSeekerElastic list")
    public void testFindAll(){
        List<JobSeekerElastic> expected = List.of(new JobSeekerElastic(
                "1214345",
                "ahmet",
                "Dayı",
                "ahmet@gmail.com",
                List.of(new PersonalSkillElastic("123123","java",10))
        ));

        when(jobSeekerElasticRepository.findAll()).thenReturn(expected);

        List<JobSeekerElastic> actual = jobSeekerElasticService.findAll();

        assertEquals(expected,actual);

        verify(jobSeekerElasticRepository).findAll();
    }

}