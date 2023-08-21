package com.obss.hrms.service;

import com.obss.hrms.converter.ApplyAdvertisementConverter;
import com.obss.hrms.converter.ApplyAdvertisementStatueConverter;
import com.obss.hrms.entity.*;
import com.obss.hrms.exception.AdvertisementApplicationNotFoundException;
import com.obss.hrms.exception.ApplyAdvertisementStatueIsWrongException;
import com.obss.hrms.exception.JobSeekerIsAlreadyApplyingAdvertisementException;
import com.obss.hrms.exception.JobSeekerIsAlreadyOccupyingBlackListException;
import com.obss.hrms.repository.ApplyAdvertisementRepository;
import com.obss.hrms.request.CreateApplyAdvertisementRequest;
import com.obss.hrms.response.CreateApplyAdvertisementResponse;
import com.obss.hrms.response.GetApplyAdvertisementResponse;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplyAdvertisementServiceTest {

    private ApplyAdvertisementRepository applyAdvertisementRepository;
    private JobSeekerService jobSeekerService;
    private AdvertisementService advertisementService;
    private MailService mailService;
    private ApplyAdvertisementStatueConverter applyAdvertisementStatueConverter;
    private ApplyAdvertisementConverter applyAdvertisementConverter;
    private ApplyAdvertisementService applyAdvertisementService;

    @BeforeEach
    void setUp() {
        applyAdvertisementRepository = mock(ApplyAdvertisementRepository.class);
        jobSeekerService = mock(JobSeekerService.class);
        advertisementService = mock(AdvertisementService.class);
        mailService = mock(MailService.class);
        applyAdvertisementStatueConverter = mock(ApplyAdvertisementStatueConverter.class);
        applyAdvertisementConverter = mock(ApplyAdvertisementConverter.class);
        applyAdvertisementService = new ApplyAdvertisementService(
                applyAdvertisementRepository,
                jobSeekerService,
                advertisementService,
                mailService,
                applyAdvertisementStatueConverter,
                applyAdvertisementConverter
        );
    }

    @Test
    public void testFindByAdvertisementId_shouldReturnGetApplyAdvertisementResponseList() {
        String advertisementId = "346546";

        JobSeeker jobSeeker = new JobSeeker(
                "1234",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                true,
                Role.USER
        );

        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "cn=mike",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        List<ApplyAdvertisement> applyAdvertisements = List.of(new ApplyAdvertisement(
                "12345",
                LocalDate.of(2001, 1, 1),
                ApplyAdvertisementStatue.PROCESSING,
                jobSeeker,
                new Advertisement(
                        advertisementId,
                        "124324",
                        "Obss",
                        "java",
                        LocalDate.of(2024, 1, 1),
                        LocalDate.of(2025, 1, 1),
                        AdvertisementStatue.PASSIVE,
                        humanResourceEntity,
                        List.of(new PersonalSkill("123456", "java", 10))
                )
        ));

        List<GetApplyAdvertisementResponse> expected = List.of(new GetApplyAdvertisementResponse(
                applyAdvertisements.get(0).getId(),
                applyAdvertisements.get(0).getApplyDate(),
                applyAdvertisements.get(0).getApplyAdvertisementStatue(),
                applyAdvertisements.get(0).getJobSeeker(),
                applyAdvertisements.get(0).getAdvertisement()
        ));
        when(applyAdvertisementRepository.findByAdvertisement_Id(advertisementId)).thenReturn(Optional.of(applyAdvertisements));
        when(applyAdvertisementConverter.convertGet(applyAdvertisements)).thenReturn(expected);

        List<GetApplyAdvertisementResponse> actual = applyAdvertisementService.findByAdvertisementId(advertisementId);

        assertEquals(expected, actual);

        verify(applyAdvertisementRepository).findByAdvertisement_Id(advertisementId);
        verify(applyAdvertisementConverter).convertGet(applyAdvertisements);
    }

    @Test
    public void testFindByJobSeekerId_shouldReturnGetApplyAdvertisementResponseList() {
        String jobSeekerId = "35245";
        JobSeeker jobSeeker = new JobSeeker(
                jobSeekerId,
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                true,
                Role.USER
        );
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "cn=mike",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        List<ApplyAdvertisement> applyAdvertisements = List.of(new ApplyAdvertisement(
                "12345",
                LocalDate.of(2001, 1, 1),
                ApplyAdvertisementStatue.PROCESSING,
                jobSeeker,
                new Advertisement(
                        "567567",
                        "124324",
                        "Obss",
                        "java",
                        LocalDate.of(2024, 1, 1),
                        LocalDate.of(2025, 1, 1),
                        AdvertisementStatue.PASSIVE,
                        humanResourceEntity,
                        List.of(new PersonalSkill("123456", "java", 10))
                )
        ));

        List<GetApplyAdvertisementResponse> expected = List.of(new GetApplyAdvertisementResponse(
                applyAdvertisements.get(0).getId(),
                applyAdvertisements.get(0).getApplyDate(),
                applyAdvertisements.get(0).getApplyAdvertisementStatue(),
                applyAdvertisements.get(0).getJobSeeker(),
                applyAdvertisements.get(0).getAdvertisement()
        ));

        when(applyAdvertisementRepository.findByJobSeeker_Id(jobSeekerId)).thenReturn(Optional.of(applyAdvertisements));
        when(applyAdvertisementConverter.convertGet(applyAdvertisements)).thenReturn(expected);

        List<GetApplyAdvertisementResponse> actual = applyAdvertisementService.findByJobSeekerId(jobSeekerId);

        assertEquals(expected, actual);

        verify(applyAdvertisementRepository).findByJobSeeker_Id(jobSeekerId);
        verify(applyAdvertisementConverter).convertGet(applyAdvertisements);


    }

    @Test
    public void testFindAll_shouldReturnGetApplyAdvertisementResponseList() {
        JobSeeker jobSeeker = new JobSeeker(
                "5686",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                true,
                Role.USER
        );
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "cn=mike",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        List<ApplyAdvertisement> applyAdvertisements = List.of(new ApplyAdvertisement(
                "12345",
                LocalDate.of(2001, 1, 1),
                ApplyAdvertisementStatue.PROCESSING,
                jobSeeker,
                new Advertisement(
                        "567567",
                        "124324",
                        "Obss",
                        "java",
                        LocalDate.of(2024, 1, 1),
                        LocalDate.of(2025, 1, 1),
                        AdvertisementStatue.PASSIVE,
                        humanResourceEntity,
                        List.of(new PersonalSkill("123456", "java", 10))
                )
        ));
        List<GetApplyAdvertisementResponse> expected = List.of(new GetApplyAdvertisementResponse(
                applyAdvertisements.get(0).getId(),
                applyAdvertisements.get(0).getApplyDate(),
                applyAdvertisements.get(0).getApplyAdvertisementStatue(),
                applyAdvertisements.get(0).getJobSeeker(),
                applyAdvertisements.get(0).getAdvertisement()
        ));

        when(applyAdvertisementRepository.findAll()).thenReturn(applyAdvertisements);
        when(applyAdvertisementConverter.convertGet(applyAdvertisements)).thenReturn(expected);

        List<GetApplyAdvertisementResponse> actual = applyAdvertisementService.findAll();

        assertEquals(expected, actual);

        verify(applyAdvertisementRepository).findAll();
        verify(applyAdvertisementConverter).convertGet(applyAdvertisements);

    }

    @Test
    public void testFilter_shouldReturnGetApplyAdvertisementResponseList() {
        String statue = "reject";
        ApplyAdvertisementStatue applyAdvertisementStatue = ApplyAdvertisementStatue.REJECT;
        JobSeeker jobSeeker = new JobSeeker(
                "5686",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                true,
                Role.USER
        );
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "cn=mike",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        List<ApplyAdvertisement> applyAdvertisements = List.of(new ApplyAdvertisement(
                "12345",
                LocalDate.of(2001, 1, 1),
                ApplyAdvertisementStatue.REJECT,
                jobSeeker,
                new Advertisement(
                        "567567",
                        "124324",
                        "Obss",
                        "java",
                        LocalDate.of(2024, 1, 1),
                        LocalDate.of(2025, 1, 1),
                        AdvertisementStatue.PASSIVE,
                        humanResourceEntity,
                        List.of(new PersonalSkill("123456", "java", 10))
                )
        ));
        List<GetApplyAdvertisementResponse> expected = List.of(new GetApplyAdvertisementResponse(
                applyAdvertisements.get(0).getId(),
                applyAdvertisements.get(0).getApplyDate(),
                applyAdvertisements.get(0).getApplyAdvertisementStatue(),
                applyAdvertisements.get(0).getJobSeeker(),
                applyAdvertisements.get(0).getAdvertisement()
        ));

        when(applyAdvertisementStatueConverter.convert(statue)).thenReturn(applyAdvertisementStatue);
        when(applyAdvertisementRepository.
                findByApplyAdvertisementStatue(applyAdvertisementStatue)).thenReturn(applyAdvertisements);
        when(applyAdvertisementConverter.convertGet(applyAdvertisements)).thenReturn(expected);

        List<GetApplyAdvertisementResponse> actual = applyAdvertisementService.filter(statue);

        assertEquals(expected, actual);

        verify(applyAdvertisementStatueConverter).convert(statue);
        verify(applyAdvertisementRepository).findByApplyAdvertisementStatue(applyAdvertisementStatue);
        verify(applyAdvertisementConverter).convertGet(applyAdvertisements);
    }

    @Test
    public void testUpdateApplyAdvertisementStatue_whenStatueIsCorrect_ShouldVoid() {
        String statue = "reject";
        String applyAdvertisementId = "45345634";
        ApplyAdvertisementStatue applyAdvertisementStatue = ApplyAdvertisementStatue.REJECT;

        JobSeeker jobSeeker = new JobSeeker(
                "5686",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                true,
                Role.USER
        );

        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "cn=mike",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );

        ApplyAdvertisement applyAdvertisement = new ApplyAdvertisement(
                applyAdvertisementId,
                LocalDate.of(2001, 1, 1),
                ApplyAdvertisementStatue.PROCESSING,
                jobSeeker,
                new Advertisement(
                        "567567",
                        "124324",
                        "Obss",
                        "java",
                        LocalDate.of(2024, 1, 1),
                        LocalDate.of(2025, 1, 1),
                        AdvertisementStatue.PASSIVE,
                        humanResourceEntity,
                        List.of(new PersonalSkill("123456", "java", 10))
                )
        );
        ApplyAdvertisement updatedApplyAdvertisement = new ApplyAdvertisement(
                applyAdvertisementId,
                LocalDate.of(2001, 1, 1),
                ApplyAdvertisementStatue.REJECT,
                jobSeeker,
                new Advertisement(
                        "567567",
                        "124324",
                        "Obss",
                        "java",
                        LocalDate.of(2024, 1, 1),
                        LocalDate.of(2025, 1, 1),
                        AdvertisementStatue.PASSIVE,
                        humanResourceEntity,
                        List.of(new PersonalSkill("123456", "java", 10))
                )
        );
        when(applyAdvertisementRepository.findById(applyAdvertisementId)).thenReturn(Optional.of(applyAdvertisement));
        when(applyAdvertisementRepository.save(updatedApplyAdvertisement)).thenReturn(updatedApplyAdvertisement);



        applyAdvertisementService.updateApplyAdvertisementStatue(statue, applyAdvertisementId);
        

        verify(applyAdvertisementRepository).findById(applyAdvertisementId);
        verify(applyAdvertisementRepository).save(updatedApplyAdvertisement);
    }

    @Test
    public void testUpdateApplyAdvertisementStatue_whenStatueIsNotCorrect_ShouldReturnString() {
        String statue = "rejec45t";
        String applyAdvertisementId = "45345634";


        JobSeeker jobSeeker = new JobSeeker(
                "5686",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                true,
                Role.USER
        );

        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "cn=mike",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );

        ApplyAdvertisement applyAdvertisement = new ApplyAdvertisement(
                applyAdvertisementId,
                LocalDate.of(2001, 1, 1),
                ApplyAdvertisementStatue.PROCESSING,
                jobSeeker,
                new Advertisement(
                        "567567",
                        "124324",
                        "Obss",
                        "java",
                        LocalDate.of(2024, 1, 1),
                        LocalDate.of(2025, 1, 1),
                        AdvertisementStatue.PASSIVE,
                        humanResourceEntity,
                        List.of(new PersonalSkill("123456", "java", 10))
                )
        );

        when(applyAdvertisementRepository.findById(applyAdvertisementId)).thenReturn(Optional.of(applyAdvertisement));

        assertThrows(ApplyAdvertisementStatueIsWrongException.class,
                () -> applyAdvertisementService.updateApplyAdvertisementStatue(statue, applyAdvertisementId));

        verify(applyAdvertisementRepository).findById(applyAdvertisementId);

    }

    @Test
    public void testUpdateApplyAdvertisementStatue_whenApplyAdvertisementIdIsNotCorrect_ShouldReturnString() {
        String statue = "rejec45t";
        String applyAdvertisementId = "45345634";

        when(applyAdvertisementRepository.findById(applyAdvertisementId)).thenReturn(Optional.empty());

        assertThrows(AdvertisementApplicationNotFoundException.class,
                () -> applyAdvertisementService.updateApplyAdvertisementStatue(statue, applyAdvertisementId));

        verify(applyAdvertisementRepository).findById(applyAdvertisementId);

    }

    @Test
    public void testCreate_whenJobSeekerInTheBlackList_shouldThrowException() {
        String applyAdvertisementStatue = "ok";
        String jobSeekerId = "345";
        String advertisementId = "1231";


        CreateApplyAdvertisementRequest request = new CreateApplyAdvertisementRequest(
                applyAdvertisementStatue,
                jobSeekerId,
                advertisementId
        );
        JobSeeker jobSeeker = new JobSeeker(
                jobSeekerId,
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                true,
                Role.USER
        );
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "cn=mike",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        Advertisement advertisement = new Advertisement(
                "567",
                "124324",
                "Obss",
                "java",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2025, 1, 1),
                AdvertisementStatue.PASSIVE,
                humanResourceEntity,
                List.of(new PersonalSkill("123456", "java", 10))
        );
        List<ApplyAdvertisement> applyAdvertisement = List.of(new ApplyAdvertisement(
                "89089",
                LocalDate.of(2001, 1, 1),
                ApplyAdvertisementStatue.PROCESSING,
                jobSeeker,
                new Advertisement(
                        "567567",
                        "124324",
                        "Obss",
                        "java",
                        LocalDate.of(2024, 1, 1),
                        LocalDate.of(2025, 1, 1),
                        AdvertisementStatue.PASSIVE,
                        humanResourceEntity,
                        List.of(new PersonalSkill("123456", "java", 10))
                )));

        when(advertisementService.findById(advertisementId)).thenReturn(advertisement);
        when(jobSeekerService.findById(jobSeekerId)).thenReturn(jobSeeker);
        when(applyAdvertisementRepository.findByJobSeeker_Id(jobSeekerId)).thenReturn(Optional.of(applyAdvertisement));

        assertThrows(JobSeekerIsAlreadyOccupyingBlackListException.class, () -> applyAdvertisementService.create(request));

        verify(advertisementService).findById(advertisementId);
        verify(jobSeekerService).findById(jobSeekerId);
        verify(applyAdvertisementRepository).findByJobSeeker_Id(jobSeekerId);
    }

    @Test
    public void testCreate_whenJobSeekerHaveAlreadyBeen_shouldThrowException() {
        String applyAdvertisementStatue = "ok";
        String jobSeekerId = "345";
        String advertisementId = "1231";


        CreateApplyAdvertisementRequest request = new CreateApplyAdvertisementRequest(
                applyAdvertisementStatue,
                jobSeekerId,
                advertisementId
        );
        JobSeeker jobSeeker = new JobSeeker(
                jobSeekerId,
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                false,
                Role.USER
        );
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "cn=mike",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        Advertisement advertisement = new Advertisement(
                advertisementId,
                "124324",
                "Obss",
                "java",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2025, 1, 1),
                AdvertisementStatue.PASSIVE,
                humanResourceEntity,
                List.of(new PersonalSkill("123456", "java", 10))
        );
        List<ApplyAdvertisement> applyAdvertisement = List.of(new ApplyAdvertisement(
                "89089",
                LocalDate.of(2001, 1, 1),
                ApplyAdvertisementStatue.PROCESSING,
                jobSeeker,
                new Advertisement(
                        advertisementId,
                        "124324",
                        "Obss",
                        "java",
                        LocalDate.of(2024, 1, 1),
                        LocalDate.of(2025, 1, 1),
                        AdvertisementStatue.PASSIVE,
                        humanResourceEntity,
                        List.of(new PersonalSkill("123456", "java", 10))
                )));

        when(advertisementService.findById(advertisementId)).thenReturn(advertisement);
        when(jobSeekerService.findById(jobSeekerId)).thenReturn(jobSeeker);
        when(applyAdvertisementRepository.findByJobSeeker_Id(jobSeekerId)).thenReturn(Optional.of(applyAdvertisement));

        assertThrows(JobSeekerIsAlreadyApplyingAdvertisementException.class, () -> applyAdvertisementService.create(request));

        verify(advertisementService).findById(advertisementId);
        verify(jobSeekerService).findById(jobSeekerId);
        verify(applyAdvertisementRepository).findByJobSeeker_Id(jobSeekerId);
    }

    @Test
    public void testCreate_shouldReturnCreateApplyAdvertisementResponse() {
        String applyAdvertisementStatue = "ok";
        ApplyAdvertisementStatue applyAdvertisementStatue1 = ApplyAdvertisementStatue.OK;
        String jobSeekerId = "345";
        String advertisementId = "1231";
        LocalDate applyDate = LocalDate.parse(LocalDate.now().toString(), DateTimeFormatter.ISO_DATE);

        CreateApplyAdvertisementRequest request = new CreateApplyAdvertisementRequest(
                applyAdvertisementStatue,
                jobSeekerId,
                advertisementId
        );
        JobSeeker jobSeeker = new JobSeeker(
                jobSeekerId,
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                false,
                Role.USER
        );
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "cn=mike",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        Advertisement advertisement = new Advertisement(
                advertisementId,
                "124324",
                "Obss",
                "java",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2025, 1, 1),
                AdvertisementStatue.PASSIVE,
                humanResourceEntity,
                List.of(new PersonalSkill("123456", "java", 10))
        );
        List<ApplyAdvertisement> applyAdvertisementList = List.of(new ApplyAdvertisement(
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
                )));
        ApplyAdvertisement applyAdvertisement = new ApplyAdvertisement(
                applyDate,
                applyAdvertisementStatue1,
                jobSeeker,
                advertisement
        );
        ApplyAdvertisement savedApplyAdvertisement = new ApplyAdvertisement(
                applyAdvertisement.getId(),
                applyAdvertisement.getApplyDate(),
                applyAdvertisement.getApplyAdvertisementStatue(),
                applyAdvertisement.getJobSeeker(),
                applyAdvertisement.getAdvertisement()
        );
        CreateApplyAdvertisementResponse expected = new CreateApplyAdvertisementResponse(
                savedApplyAdvertisement.getId(),
                savedApplyAdvertisement.getApplyDate(),
                savedApplyAdvertisement.getApplyAdvertisementStatue(),
                savedApplyAdvertisement.getJobSeeker(),
                savedApplyAdvertisement.getAdvertisement()
        );

        when(advertisementService.findById(advertisementId)).thenReturn(advertisement);
        when(jobSeekerService.findById(jobSeekerId)).thenReturn(jobSeeker);
        when(applyAdvertisementRepository.findByJobSeeker_Id(jobSeekerId)).thenReturn(Optional.of(applyAdvertisementList));
        when(applyAdvertisementStatueConverter.convert(applyAdvertisementStatue)).thenReturn(applyAdvertisementStatue1);
        when(applyAdvertisementRepository.save(applyAdvertisement)).thenReturn(savedApplyAdvertisement);
        when(applyAdvertisementConverter.convert(savedApplyAdvertisement)).thenReturn(expected);

        CreateApplyAdvertisementResponse actual = applyAdvertisementService.create(request);

        assertEquals(expected, actual);

        verify(advertisementService).findById(advertisementId);
        verify(jobSeekerService).findById(jobSeekerId);
        verify(applyAdvertisementRepository).findByJobSeeker_Id(jobSeekerId);
        verify(applyAdvertisementStatueConverter).convert(applyAdvertisementStatue);
        verify(applyAdvertisementRepository).save(applyAdvertisement);
        verify(applyAdvertisementConverter).convert(savedApplyAdvertisement);
    }

    @Test
    public void testFindAllApplyAdvertisementsSortedByMatchCount_shouldReturn() {
        JobSeeker jobSeeker = new JobSeeker(
                "8908",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                false,
                Role.USER,
                List.of(new PersonalSkill("1232", "java", 5))
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
        List<ApplyAdvertisement> applyAdvertisementList = List.of(advertisement);
        List<GetApplyAdvertisementResponse> expected = List.of(new GetApplyAdvertisementResponse(
                applyAdvertisementList.get(0).getId(),
                applyAdvertisementList.get(0).getApplyDate(),
                applyAdvertisementList.get(0).getApplyAdvertisementStatue(),
                applyAdvertisementList.get(0).getJobSeeker(),
                applyAdvertisementList.get(0).getAdvertisement()
        ));
        when(applyAdvertisementRepository.findAll()).thenReturn(applyAdvertisementList);
        when(applyAdvertisementRepository.findByAdvertisement_IdAndJobSeeker_Id(
                applyAdvertisementList.get(0).getAdvertisement().getId(), jobSeeker.getId())).thenReturn(advertisement);
        when(applyAdvertisementConverter.convertGet(applyAdvertisementList)).thenReturn(expected);
        List<GetApplyAdvertisementResponse> actual = applyAdvertisementService.findAllApplyAdvertisementsSortedByMatchCount();
        assertEquals(expected, actual);


    }

    @Test
    @DisplayName("find all apply advertisement and return apply advertisement list")
    public void testFindAll() {
        JobSeeker jobSeeker = new JobSeeker(
                "8908",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                false,
                Role.USER,
                List.of(new PersonalSkill("1232", "java", 5))
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
        List<ApplyAdvertisement> expected = List.of(advertisement);

        when(applyAdvertisementRepository.findAll()).thenReturn(expected);

        List<ApplyAdvertisement> actual = applyAdvertisementService.findAllApplyAdvertisement();

        assertEquals(expected, actual);

        verify(applyAdvertisementRepository).findAll();
    }
}