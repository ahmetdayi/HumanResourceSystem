package com.obss.hrms.service;

import com.obss.hrms.converter.BlackListConverter;
import com.obss.hrms.entity.*;
import com.obss.hrms.exception.*;
import com.obss.hrms.repository.BlackListRepository;
import com.obss.hrms.request.CreateBlackListRequest;
import com.obss.hrms.response.CreateBlackListResponse;
import com.obss.hrms.response.GetBlackListResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.ldap.LdapName;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlackListServiceTest {



    private  BlackListRepository blackListRepository;
    private  JobSeekerService jobSeekerService;
    private  HumanResourceService humanResourceService;
    private  ApplyAdvertisementService applyAdvertisementService;
    private  BlackListConverter blackListConverter;
    private BlackListService blackListService;

    @BeforeEach
    void setUp() {
        blackListRepository = mock(BlackListRepository.class);
        jobSeekerService = mock(JobSeekerService.class);
        humanResourceService = mock(HumanResourceService.class);
        applyAdvertisementService = mock(ApplyAdvertisementService.class);
        blackListConverter = mock(BlackListConverter.class);

        blackListService = new BlackListService(
                blackListRepository,
                jobSeekerService,
                humanResourceService,
                applyAdvertisementService,
                blackListConverter
        );
    }
    @Test
    public void testFindAll_shouldReturnGetBlackListResponseList(){
        List<BlackList> blackLists = List.of(new BlackList(
                "123",
                "you are in the black list",
                new JobSeeker(
                        "1234",
                        "Ahmet",
                        "Dayi",
                        "ahmet@gmailcom",
                        Role.USER

                ),
                new HumanResourceEntity(
                        "cn=ahmet",
                        "ahmet",
                        "dayi",
                        "ahmet dayi"
                )
        ));
        List<GetBlackListResponse> expected = List.of(new GetBlackListResponse(
                "123",
                "you are in the black list",
                new JobSeeker(
                        "1234",
                        "Ahmet",
                        "Dayi",
                        "ahmet@gmailcom",
                        Role.USER

                ),
                new HumanResourceEntity(
                        "cn=ahmet",
                        "ahmet",
                        "dayi",
                        "ahmet dayi"
                )
        ));

        when(blackListRepository.findAll()).thenReturn(blackLists);
        when(blackListConverter.convertGet(blackLists)).thenReturn(expected);
        List<GetBlackListResponse> actual = blackListService.findAll();

        assertEquals(expected,actual);

        verify(blackListRepository).findAll();
        verify(blackListConverter).convertGet(blackLists);
    }

    @Test
    public void testDelete_whenIdExist_shouldReturnVoid(){
        String blackListId = "12345";
        JobSeeker jobSeeker = new JobSeeker(
                "123",
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                true,
                Role.USER
        );
        BlackList blackList = new BlackList(
                blackListId,
                "you are in the black list",
               jobSeeker,
                new HumanResourceEntity(
                        "cn=ahmet",
                        "ahmet",
                        "dayi",
                        "ahmet dayi"
                )
        );
        when(blackListRepository.findById(blackListId)).thenReturn(Optional.of(blackList));


        blackListService.delete(blackListId);

        verify(jobSeekerService).updateInBlackList(jobSeeker,false);
        verify(blackListRepository).findById(blackListId);
        verify(blackListRepository).delete(blackList);
    }
    @Test
    public void testDelete_whenIdDoesntExist_shouldThrowException(){
        String blackListId = "12345";
        when(blackListRepository.findById(blackListId)).thenReturn(Optional.empty());
        assertThrows(BlackListNotFoundException.class, ()-> blackListService.delete(blackListId));

        verify(blackListRepository).findById(blackListId);
    }
    @Test
    public void testCreateBlackList_whenJobSeekerAndHumanResourceAndBlackListExist_shouldThrowException() throws InvalidNameException {
        CreateBlackListRequest request = new CreateBlackListRequest(
                "you are in the black list.",
                "123",
                "cn=ahmet"
        );
        Name name = new LdapName(request.humanResourceId());
        JobSeeker jobSeeker = new JobSeeker(
                request.jobSeekerId(),
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                true,
                Role.USER
        );
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                        request.humanResourceId(),
                        "ahmet",
                        "dayi",
                        "ahmet dayi"
                );
        HumanResource humanResource = new HumanResource(
                name,
                "ahmet",
                "dayi",
                "ahmet dayi",
                "123"
        );
        CreateBlackListResponse expected = new CreateBlackListResponse(
                "1234",
                "you are in the black list",
                jobSeeker,
                humanResourceEntity
        );
        BlackList blackList = new BlackList(
                "12345",
                "you are in the black list",
                jobSeeker,
               humanResourceEntity
        );
        when(humanResourceService.findNameByDn(name)).thenReturn(humanResource);
        when(jobSeekerService.findById(request.jobSeekerId())).thenReturn(jobSeeker);
        when(blackListRepository.findByJobSeeker_Id(jobSeeker.getId())).thenThrow(JobSeekerIsAlreadyOccupyingBlackListException.class);

        assertThrows(JobSeekerIsAlreadyOccupyingBlackListException.class,()->blackListService.create(request));

        verify(humanResourceService).findNameByDn(name);
        verify(jobSeekerService).findById(request.jobSeekerId());
        verify(blackListRepository).findByJobSeeker_Id(jobSeeker.getId());

    }
    @Test
    public void testCreateBlackList_whenJobSeekerDoesntExist_shouldThrowException() throws InvalidNameException {
        CreateBlackListRequest request = new CreateBlackListRequest(
                "you are in the black list.",
                "123",
                "cn=ahmet"
        );
        Name name = new LdapName(request.humanResourceId());
        HumanResource humanResource = new HumanResource(
                name,
                "ahmet",
                "dayi",
                "ahmet dayi",
                "123"
        );
        when(humanResourceService.findNameByDn(name)).thenReturn(humanResource);
        when(jobSeekerService.findById(request.jobSeekerId())).thenThrow(JobSeekerNotFoundException.class);

        assertThrows(JobSeekerNotFoundException.class,()->blackListService.create(request));

        verify(jobSeekerService).findById(request.jobSeekerId());
        verify(humanResourceService).findNameByDn(name);
    }
    @Test
    public void testCreateBlackList_whenHumanResourceDoesntExist_shouldThrowException() throws InvalidNameException{
        CreateBlackListRequest request = new CreateBlackListRequest(
                "you are in the black list.",
                "123",
                "cn=ahmet"
        );
        Name name = new LdapName(request.humanResourceId());
        when(humanResourceService.findNameByDn(name)).thenThrow(HumanResourceNotFoundException.class);
        assertThrows(HumanResourceNotFoundException.class,()->blackListService.create(request));

        verify(humanResourceService).findNameByDn(name);
    }



    @Test
    public void testCreateBlackList_whenJobSeekerAndHumanResourceAndBlackListDoesntExist_shouldReturnCreateBlackListResponse() throws InvalidNameException {
        CreateBlackListRequest request = new CreateBlackListRequest(
                "you are in the black list.",
                "123",
                "cn=ahmet"
        );
        Name name = new LdapName(request.humanResourceId());

        JobSeeker jobSeeker = new JobSeeker(
                request.jobSeekerId(),
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                false,
                Role.USER
        );
        JobSeeker changeJobSeeker = new JobSeeker(
                request.jobSeekerId(),
                "Ahmet",
                "Dayı",
                "ahmet@gmail.com",
                true,
                Role.USER
        );
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                request.humanResourceId(),
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        HumanResource humanResource = new HumanResource(
                name,
                "ahmet",
                "dayi",
                "ahmet dayi",
                "123"
        );

        BlackList blackList = new BlackList(
                request.description(),
                changeJobSeeker,
                humanResourceEntity
        );
        BlackList savedBlackList = new BlackList(
                "12345",
                blackList.getDescription(),
                blackList.getJobSeeker(),
                blackList.getHumanResourceEntity()
        );
        List<ApplyAdvertisement> applyAdvertisements = List.of(new ApplyAdvertisement(
                "12345",
                LocalDate.of(2001,1,1),
                ApplyAdvertisementStatue.PROCESSING,
                jobSeeker,
                new Advertisement(
                        "1565",
                        "124324",
                        "Obss",
                        "java",
                        LocalDate.of(2024,1,1),
                        LocalDate.of(2025,1,1),
                        AdvertisementStatue.PASSIVE,
                        humanResourceEntity,
                        List.of(new PersonalSkill("123456","java",10))
                )
        ));
        List<ApplyAdvertisement> changedApplyAdvertisements = List.of(new ApplyAdvertisement(
                "12345",
                LocalDate.of(2001,1,1),
                ApplyAdvertisementStatue.REJECT,
                jobSeeker,
                new Advertisement(
                        "1565",
                        "124324",
                        "Obss",
                        "java",
                        LocalDate.of(2024,1,1),
                        LocalDate.of(2025,1,1),
                        AdvertisementStatue.PASSIVE,
                        humanResourceEntity,
                        List.of(new PersonalSkill("123456","java",10))
                )
        ));
        CreateBlackListResponse expected = new CreateBlackListResponse(
                "1234",
                "you are in the black list",
                changeJobSeeker,
                humanResourceEntity
        );
        when(humanResourceService.findNameByDn(name)).thenReturn(humanResource);
        when(jobSeekerService.findById(request.jobSeekerId())).thenReturn(jobSeeker);
        when(blackListRepository.findByJobSeeker_Id(request.jobSeekerId())).thenReturn(Optional.empty());
        doAnswer(invocation -> {
            JobSeeker jobSeekerArgument = invocation.getArgument(0);
            boolean valueArgument = invocation.getArgument(1);
            // Burada jobSeeker'in inBlackList değişkenini doğru şekilde değiştirin
            jobSeekerArgument.setInBlackList(valueArgument);
            return null;
        }).when(jobSeekerService).updateInBlackList(any(JobSeeker.class), anyBoolean());
        when(blackListRepository.findByJobSeeker_Id(request.jobSeekerId())).thenReturn(Optional.empty());

        when(applyAdvertisementService.findByJobSeeker_Id(request.jobSeekerId())).thenReturn(applyAdvertisements);
        when(blackListRepository.save(any(BlackList.class))).thenAnswer(invocation -> {
            BlackList savedBlackList1 = invocation.getArgument(0);
            savedBlackList1 = new BlackList(
                    "12345",
                    blackList.getDescription(),
                    blackList.getJobSeeker(),
                    blackList.getHumanResourceEntity()
            );
            // Burada kaydedilen BlackList nesnesini değiştirerek uygun bir cevap vermek gerekebilir.
            // Örnek olarak, savedBlackList nesnesinin bir ID değeri atanmış olarak geri dönmesini sağlayabilirsiniz.
            return savedBlackList1;
        });
        when(blackListConverter.convert(savedBlackList)).thenReturn(expected);

        CreateBlackListResponse actual = blackListService.create(request);

        assertEquals(expected,actual);

        verify(humanResourceService).findNameByDn(name);
        verify(jobSeekerService).findById(request.jobSeekerId());
        verify(blackListRepository).findByJobSeeker_Id(request.jobSeekerId());
        verify(jobSeekerService).updateInBlackList(jobSeeker,true);
        verify(blackListRepository).save(blackList);
        verify(blackListConverter).convert(savedBlackList);

    }

}