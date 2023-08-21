package com.obss.hrms.service;

import com.obss.hrms.converter.PersonalSkillConverter;
import com.obss.hrms.entity.PersonalSkill;
import com.obss.hrms.exception.PersonalSkillNotFoundException;
import com.obss.hrms.repository.PersonalSkillRepository;
import com.obss.hrms.request.CreatePersonalSkillRequest;
import com.obss.hrms.response.CreatePersonalSkillResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonalSkillServiceTest {

    private  PersonalSkillRepository personalSkillRepository;
    private  PersonalSkillConverter personalSkillConverter;
    private PersonalSkillService personalSkillService;

    @BeforeEach
    void setUp() {
        personalSkillRepository = mock(PersonalSkillRepository.class);
        personalSkillConverter = mock(PersonalSkillConverter.class);
        personalSkillService = new PersonalSkillService(personalSkillRepository,personalSkillConverter);
    }

    @Test
    public void testCreatePersonalSkill_shouldReturnCreatePersonalSkillResponse(){
        CreatePersonalSkillRequest request = new CreatePersonalSkillRequest(
                "Java",
                10
        );

        PersonalSkill personalSkill = new PersonalSkill(
                request.name(),
                request.level()
        );
        PersonalSkill savePersonalSkill = new PersonalSkill(
                "23423424",
                request.name(),
                request.level()
        );
        CreatePersonalSkillResponse expected = new CreatePersonalSkillResponse(
                savePersonalSkill.getId(),
                savePersonalSkill.getName(),
                savePersonalSkill.getLevel()
        );
        when(personalSkillRepository.save(personalSkill)).thenReturn(savePersonalSkill);
        when(personalSkillConverter.convert(savePersonalSkill)).thenReturn(expected);
        CreatePersonalSkillResponse result = personalSkillService.create(request);
        assertEquals(expected,result);

        verify(personalSkillRepository).save(personalSkill);
        verify(personalSkillConverter).convert(savePersonalSkill);
    }
    @Test
    public void testDeletePersonalSkill_whenPersonalSkillIdExist_shouldDeletePersonalSkill(){
        String personalSkillId = "1231432";
        PersonalSkill personalSkill = new PersonalSkill(
          personalSkillId,
          "Java",
          10
        );

        when(personalSkillRepository.findById(personalSkillId)).thenReturn(Optional.of(personalSkill));
        personalSkillService.delete(personalSkill.getId());

        verify(personalSkillRepository).findById(personalSkillId);
        verify(personalSkillRepository).delete(personalSkill);
    }
    @Test
    public void testDeletePersonalSkill_whenPersonalSkillIdDoesntExist_shouldThrowException(){
        String personalSkillId = "1231432";

        when(personalSkillRepository.findById(personalSkillId)).thenReturn(Optional.empty());

        assertThrows(PersonalSkillNotFoundException.class,()-> personalSkillService.delete(personalSkillId));
        verify(personalSkillRepository).findById(personalSkillId);
    }
    @Test
    @DisplayName("fin all personal skill and return all personal skill")
    public void testFindAll(){
        List<PersonalSkill> expected = List.of(new PersonalSkill(
                        "1123",
                        "java",
                        10)
        );
        when(personalSkillRepository.findAll()).thenReturn(expected);

        List<PersonalSkill> actual = personalSkillService.findAllPersonalSkill();

        assertEquals(expected,actual);

        verify(personalSkillRepository).findAll();

    }
}