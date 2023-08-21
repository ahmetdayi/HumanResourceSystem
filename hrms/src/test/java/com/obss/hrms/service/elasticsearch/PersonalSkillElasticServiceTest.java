package com.obss.hrms.service.elasticsearch;

import com.obss.hrms.entity.elasticsearch.PersonalSkillElastic;
import com.obss.hrms.repository.elasticsearch.PersonalSkillElasticRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonalSkillElasticServiceTest {

    private PersonalSkillElasticRepository personalSkillElasticRepository;

    private PersonalSkillElasticService personalSkillElasticService;
    @BeforeEach
    void setUp() {
        personalSkillElasticRepository = mock(PersonalSkillElasticRepository.class);
        personalSkillElasticService = new PersonalSkillElasticService(personalSkillElasticRepository);
    }
    @Test
    @DisplayName("save all personal skill elastic")
    public void testSaveAll(){
        List<PersonalSkillElastic> personalSkillElastic = List.of(new PersonalSkillElastic(
                "123",
                "java",
                10
        ));
        personalSkillElasticService.saveAll(personalSkillElastic);

        verify(personalSkillElasticRepository).saveAll(personalSkillElastic);
    }
    @Test
    @DisplayName("find all personal skill elastic and return PersonalSkillElastic list ")
    public void testFindAll(){
        List<PersonalSkillElastic> expected = List.of(new PersonalSkillElastic(
                "123",
                "java",
                10
        ));
        when(personalSkillElasticRepository.findAll()).thenReturn(expected);
        List<PersonalSkillElastic> actual = personalSkillElasticService.findAll();

        assertEquals(expected,actual);

        verify(personalSkillElasticRepository).findAll();
    }
}