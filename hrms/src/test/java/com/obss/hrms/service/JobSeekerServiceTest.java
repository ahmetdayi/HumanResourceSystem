package com.obss.hrms.service;

import com.obss.hrms.converter.JobSeekerConverter;
import com.obss.hrms.entity.JobSeeker;
import com.obss.hrms.entity.PersonalSkill;
import com.obss.hrms.entity.Role;
import com.obss.hrms.exception.JobSeekerNotFoundException;
import com.obss.hrms.repository.JobSeekerRepository;
import com.obss.hrms.request.AddPersonalSkillInJobSeekerRequest;
import com.obss.hrms.request.UpdateJobSeekerRequest;
import com.obss.hrms.response.GetJobSeekerResponse;
import com.obss.hrms.response.UpdateJobSeekerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JobSeekerServiceTest {

    private JobSeekerRepository jobSeekerRepository;
    private JobSeekerConverter jobSeekerConverter;

    private PersonalSkillService personalSkillService;

    private JobSeekerService jobSeekerService;


    @BeforeEach
    void setUp() {
        jobSeekerRepository = mock(JobSeekerRepository.class);
        jobSeekerConverter = mock(JobSeekerConverter.class);
        personalSkillService = mock(PersonalSkillService.class);
        jobSeekerService = new JobSeekerService(
                jobSeekerRepository,
                jobSeekerConverter,
                personalSkillService
        );

    }

    @Test
    public void testSave_shouldSaveJobSeeker() {
        JobSeeker jobSeeker = new JobSeeker(
                "1214345",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                Role.USER
        );
        jobSeekerService.save(jobSeeker);
        verify(jobSeekerRepository).save(jobSeeker);
    }

    @Test
    public void testUpdate_whenEmailUnique_shouldReturnUpdateResponse() {
        UpdateJobSeekerRequest request = new UpdateJobSeekerRequest(
                "2342341",
                "Ahmet",
                "Dayı",
                "2001-03-03",
                "I am Ahmet"
        );
        JobSeeker jobSeeker = new JobSeeker(
                "2342341",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                false,
                Role.USER
        );

        JobSeeker updateJobSeeker = new JobSeeker(
                "2342341",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                LocalDate.parse(request.birthDay(), DateTimeFormatter.ISO_DATE),
                "I am Ahmet",
                false,
                Role.USER,
                null
        );

        JobSeeker saveJobSeeker = new JobSeeker(
                updateJobSeeker.getId(),
                updateJobSeeker.getFirstname(),
                updateJobSeeker.getLastName(),
                updateJobSeeker.getEmail(),
                updateJobSeeker.getBirthDay(),
                updateJobSeeker.getDescription(),
                updateJobSeeker.getInBlackList(),
                updateJobSeeker.getRole(),
                updateJobSeeker.getPersonalSkillList()
        );

        UpdateJobSeekerResponse expected = new UpdateJobSeekerResponse(
                "2342341",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                LocalDate.of(2003, 3, 3),
                "I am Ahmet",
                false,
                null
        );
        when(jobSeekerRepository.findById(request.id())).thenReturn(Optional.of(jobSeeker));
        when(jobSeekerRepository.save(updateJobSeeker)).thenReturn(saveJobSeeker);
        when(jobSeekerConverter.convert(saveJobSeeker)).thenReturn(expected);

        UpdateJobSeekerResponse actual = jobSeekerService.update(request);

        assertEquals(expected, actual);

        verify(jobSeekerRepository).findById(request.id());
        verify(jobSeekerRepository).save(updateJobSeeker);
        verify(jobSeekerConverter).convert(saveJobSeeker);

    }

    @Test
    public void testUpdate_whenIdDoesntExist_ShouldThrowException() {
        UpdateJobSeekerRequest request = new UpdateJobSeekerRequest(
                "2342341",
                "Ahmet",
                "Dayı",
                "2001-03-03",
                "I am Ahmet"
        );
        when(jobSeekerRepository.findById(request.id())).thenReturn(Optional.empty());

        assertThrows(JobSeekerNotFoundException.class, () -> jobSeekerService.update(request));

        verify(jobSeekerRepository).findById(request.id());
    }

    @Test
    public void testFindJobSeekerById_whenIdExist_ShouldReturnGetJobSeekerResponse() {
        String jobSeekerId = "12334";

        JobSeeker jobSeeker = new JobSeeker(
                jobSeekerId,
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                false,
                Role.USER
        );
        GetJobSeekerResponse expected = new GetJobSeekerResponse(
                jobSeekerId,
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                LocalDate.of(2003, 3, 3),
                "I am Ahmet",
                false,
                null
        );

        when(jobSeekerRepository.findById(jobSeekerId)).thenReturn(Optional.of(jobSeeker));
        when(jobSeekerConverter.convertGet(jobSeeker)).thenReturn(expected);

        GetJobSeekerResponse actual = jobSeekerService.findJobSeekerById(jobSeekerId);

        assertEquals(expected, actual);

        verify(jobSeekerRepository).findById(jobSeekerId);
        verify(jobSeekerConverter).convertGet(jobSeeker);
    }

    @Test
    public void testFindJobSeekerById_whenIdDoesntExist_ShouldThrowException() {
        String jobSeekerId = "12334";

        when(jobSeekerRepository.findById(jobSeekerId)).thenReturn(Optional.empty());
        assertThrows(JobSeekerNotFoundException.class, () -> jobSeekerService.findById(jobSeekerId));

        verify(jobSeekerRepository).findById(jobSeekerId);
    }

    @Test
    public void testFindAll_ShouldReturnGetJobSeekerResponseList() {
        List<JobSeeker> jobSeekers = List.of(new JobSeeker(
                "1234",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                false,
                Role.USER
        ), new JobSeeker(
                "12345",
                "Ahmet",
                "Dayı",
                "ahmet1@gmail.com",
                false,
                Role.USER
        ));
        List<GetJobSeekerResponse> expect = List.of(new GetJobSeekerResponse(
                "1234",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                LocalDate.of(2003, 3, 3),
                "I am Ahmet",
                false,
                null
        ), new GetJobSeekerResponse(
                "12345",
                "Ahmet",
                "Dayı",
                "ahmet1@gmail.com",
                LocalDate.of(2003, 3, 3),
                "I am Ahmet",
                false,
                null
        ));

        when(jobSeekerRepository.findAll()).thenReturn(jobSeekers);
        when(jobSeekerConverter.convertGet(jobSeekers)).thenReturn(expect);

        List<GetJobSeekerResponse> actual = jobSeekerService.findAll();

        assertEquals(expect,actual);

        verify(jobSeekerRepository).findAll();
        verify(jobSeekerConverter).convertGet(jobSeekers);
    }

    @Test
    public void testAddPersonalSkill_whenJobSeekerPersonalSkillsDoesntExist_shouldReturnGetJobSeekerResponse(){
        AddPersonalSkillInJobSeekerRequest request = new AddPersonalSkillInJobSeekerRequest(
                "12345",
                List.of("123"));

        PersonalSkill personalSkill = new PersonalSkill(
                "123",
                "java",
                10
        );
        JobSeeker jobSeeker = new JobSeeker(
                "12345",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                false,
                Role.USER
        );
        JobSeeker saveJobSeeker = new JobSeeker(
                "12345",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                false,
                Role.USER,
                List.of(new PersonalSkill(
                        "123",
                        "java",
                        10
                )
        ));
        GetJobSeekerResponse expected = new GetJobSeekerResponse(
                "12345",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                LocalDate.of(2003, 3, 3),
                "I am Ahmet",
                false,
                List.of(new PersonalSkill(
                        "123",
                        "java",
                        10
                ))
        );
        when(personalSkillService.findByIdIn(request.personalSkillIdList())).thenReturn(List.of(personalSkill));
        when(jobSeekerRepository.findById(request.jobSeekerId())).thenReturn(Optional.of(jobSeeker));
        when(jobSeekerRepository.save(jobSeeker)).thenReturn(saveJobSeeker);
        when(jobSeekerConverter.convertGet(saveJobSeeker)).thenReturn(expected);

        GetJobSeekerResponse actual = jobSeekerService.addPersonalSkill(request);

        assertEquals(expected,actual);

        verify(personalSkillService).findByIdIn(request.personalSkillIdList());
        verify(jobSeekerRepository).findById(request.jobSeekerId());
        verify(jobSeekerRepository).save(jobSeeker);
        verify(jobSeekerConverter).convertGet(saveJobSeeker);
    }
    @Test
    public void testAddPersonalSkill_whenJobSeekerPersonalSkillsExistAndLevelsAreSame_shouldReturnGetJobSeekerResponse(){
        AddPersonalSkillInJobSeekerRequest request = new AddPersonalSkillInJobSeekerRequest(
                "12345",
                List.of("123"));

        PersonalSkill personalSkill = new PersonalSkill(
                "123",
                "java",
                10
        );
        PersonalSkill jobSeekerPersonalSkill = new PersonalSkill(
                "123",
                "java",
                10
        );
        JobSeeker jobSeeker = new JobSeeker(
                "12345",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                false,
                Role.USER,
                List.of(jobSeekerPersonalSkill)
        );
        JobSeeker saveJobSeeker = new JobSeeker(
                "12345",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                false,
                Role.USER,
                List.of(new PersonalSkill(
                                "123",
                                "java",
                                10
                        )
                ));
        GetJobSeekerResponse expected = new GetJobSeekerResponse(
                "12345",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                LocalDate.of(2003, 3, 3),
                "I am Ahmet",
                false,
                List.of(new PersonalSkill(
                        "123",
                        "java",
                        10
                ))
        );
        when(personalSkillService.findByIdIn(request.personalSkillIdList())).thenReturn(List.of(personalSkill));
        when(jobSeekerRepository.findById(request.jobSeekerId())).thenReturn(Optional.of(jobSeeker));
        when(jobSeekerRepository.save(jobSeeker)).thenReturn(saveJobSeeker);
        when(jobSeekerConverter.convertGet(saveJobSeeker)).thenReturn(expected);

        GetJobSeekerResponse actual = jobSeekerService.addPersonalSkill(request);

        assertEquals(expected,actual);

        verify(personalSkillService).findByIdIn(request.personalSkillIdList());
        verify(jobSeekerRepository).findById(request.jobSeekerId());
        verify(jobSeekerRepository).save(jobSeeker);
        verify(jobSeekerConverter).convertGet(saveJobSeeker);
    }

    @Test
    public void testAddPersonalSkill_whenJobSeekerPersonalSkillsExistAndRequestLevelBiggerThanJobSeekerLevel_shouldReturnGetJobSeekerResponse(){
        AddPersonalSkillInJobSeekerRequest request = new AddPersonalSkillInJobSeekerRequest(
                "12345",
                List.of("123"));

        PersonalSkill personalSkill = new PersonalSkill(
                "123",
                "java",
                10
        );
        PersonalSkill jobSeekerPersonalSkill = new PersonalSkill(
                "123",
                "java",
                9
        );
        JobSeeker jobSeeker = new JobSeeker(
                "12345",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                false,
                Role.USER,
                List.of(jobSeekerPersonalSkill)
        );
        JobSeeker saveJobSeeker = new JobSeeker(
                "12345",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                false,
                Role.USER,
                List.of(new PersonalSkill(
                                "123",
                                "java",
                                10
                        )
                ));
        GetJobSeekerResponse expected = new GetJobSeekerResponse(
                "12345",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                LocalDate.of(2003, 3, 3),
                "I am Ahmet",
                false,
                List.of(new PersonalSkill(
                        "123",
                        "java",
                        10
                ))
        );
        when(personalSkillService.findByIdIn(request.personalSkillIdList())).thenReturn(List.of(personalSkill));
        when(jobSeekerRepository.findById(request.jobSeekerId())).thenReturn(Optional.of(jobSeeker));
        when(jobSeekerRepository.save(jobSeeker)).thenReturn(saveJobSeeker);
        when(jobSeekerConverter.convertGet(saveJobSeeker)).thenReturn(expected);

        GetJobSeekerResponse actual = jobSeekerService.addPersonalSkill(request);

        assertEquals(expected,actual);

        verify(personalSkillService).findByIdIn(request.personalSkillIdList());
        verify(jobSeekerRepository).findById(request.jobSeekerId());
        verify(jobSeekerRepository).save(jobSeeker);
        verify(jobSeekerConverter).convertGet(saveJobSeeker);
    }
    @Test
    public void testAddPersonalSkill_whenJobSeekerPersonalSkillsExistAndJobSeekerLevelBiggerThanRequestLevel_shouldReturnGetJobSeekerResponse(){
        AddPersonalSkillInJobSeekerRequest request = new AddPersonalSkillInJobSeekerRequest(
                "12345",
                List.of("123"));

        PersonalSkill personalSkill = new PersonalSkill(
                "123",
                "java",
                9
        );
        PersonalSkill jobSeekerPersonalSkill = new PersonalSkill(
                "123",
                "java",
                10
        );
        JobSeeker jobSeeker = new JobSeeker(
                "12345",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                false,
                Role.USER,
                List.of(jobSeekerPersonalSkill)
        );
        JobSeeker saveJobSeeker = new JobSeeker(
                "12345",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                false,
                Role.USER,
                List.of(new PersonalSkill(
                                "123",
                                "java",
                                10
                        )
                ));
        GetJobSeekerResponse expected = new GetJobSeekerResponse(
                "12345",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                LocalDate.of(2003, 3, 3),
                "I am Ahmet",
                false,
                List.of(new PersonalSkill(
                        "123",
                        "java",
                        10
                ))
        );
        when(personalSkillService.findByIdIn(request.personalSkillIdList())).thenReturn(List.of(personalSkill));
        when(jobSeekerRepository.findById(request.jobSeekerId())).thenReturn(Optional.of(jobSeeker));
        when(jobSeekerRepository.save(jobSeeker)).thenReturn(saveJobSeeker);
        when(jobSeekerConverter.convertGet(saveJobSeeker)).thenReturn(expected);

        GetJobSeekerResponse actual = jobSeekerService.addPersonalSkill(request);

        assertEquals(expected,actual);

        verify(personalSkillService).findByIdIn(request.personalSkillIdList());
        verify(jobSeekerRepository).findById(request.jobSeekerId());
        verify(jobSeekerRepository).save(jobSeeker);
        verify(jobSeekerConverter).convertGet(saveJobSeeker);
    }

    @Test
    @DisplayName("fin all job seekers and return jobs seekers")
    public void testFindAll(){
        PersonalSkill jobSeekerPersonalSkill = new PersonalSkill(
                "123",
                "java",
                10
        );
        List<JobSeeker> expected = List.of(new JobSeeker(
                "12345",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                false,
                Role.USER,
                List.of(jobSeekerPersonalSkill)
        ));
        when(jobSeekerRepository.findAll()).thenReturn(expected);

        List<JobSeeker> actual = jobSeekerService.findAllJobSeeker();

        assertEquals(expected,actual);

        verify(jobSeekerRepository).findAll();
    }
}