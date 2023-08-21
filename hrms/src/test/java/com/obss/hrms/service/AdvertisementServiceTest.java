package com.obss.hrms.service;

import com.obss.hrms.converter.AdvertisementConverter;
import com.obss.hrms.converter.AdvertisementStatueConverter;
import com.obss.hrms.entity.*;
import com.obss.hrms.exception.AdvertisementHaveAlreadyBeenCreatingException;
import com.obss.hrms.exception.AvdCodeHasBeUniqueException;
import com.obss.hrms.exception.HumanResourceNotFoundException;
import com.obss.hrms.repository.AdvertisementRepository;
import com.obss.hrms.request.CreateAdvertisementRequest;
import com.obss.hrms.request.UpdateAdvertisementRequest;
import com.obss.hrms.response.CreateAdvertisementResponse;
import com.obss.hrms.response.GetAdvertisementResponse;
import com.obss.hrms.response.UpdateAdvertisementResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.ldap.LdapName;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdvertisementServiceTest {


    private AdvertisementRepository advertisementRepository;
    private HumanResourceService humanResourceService;
    private PersonalSkillService personalSkillService;

    private AdvertisementConverter advertisementConverter;
    private AdvertisementStatueConverter advertisementStatueConverter;

    private AdvertisementService advertisementService;

    @BeforeEach
    void setUp() {
        advertisementRepository = mock(AdvertisementRepository.class);
        humanResourceService = mock(HumanResourceService.class);
        personalSkillService = mock(PersonalSkillService.class);
        advertisementConverter = mock(AdvertisementConverter.class);
        advertisementStatueConverter = mock(AdvertisementStatueConverter.class);
        advertisementService = new AdvertisementService(
                advertisementRepository,
                humanResourceService,
                personalSkillService,
                advertisementConverter,
                advertisementStatueConverter
        );
    }

    @Test
    public void testFindAll_ShouldReturnGetAdvertisementResponseList() {
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "436345",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        List<GetAdvertisementResponse> expected = List.of(new GetAdvertisementResponse(
                "1565",
                "124324",
                "Obss",
                "java",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2025, 1, 1),
                AdvertisementStatue.PASSIVE,
                humanResourceEntity,
                List.of(new PersonalSkill("123456", "java", 10))
        ));
        List<Advertisement> advertisement = List.of(new Advertisement(
                "1565",
                "124324",
                "Obss",
                "java",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2025, 1, 1),
                AdvertisementStatue.PASSIVE,
                humanResourceEntity,
                List.of(new PersonalSkill("123456", "java", 10))
        ));

        when(advertisementRepository.findAll()).thenReturn(advertisement);
        when(advertisementConverter.convertGet(advertisement)).thenReturn(expected);

        List<GetAdvertisementResponse> actual = advertisementService.findAll();
        assertEquals(expected, actual);

        verify(advertisementRepository).findAll();
        verify(advertisementConverter).convertGet(advertisement);
    }

    @Test
    public void testChangeAdvertisementStatue_whenAdvertisementIdExist_shouldReturnString() {
        boolean statue = false;
        String advertisementId = "46456";
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "436345",
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
                AdvertisementStatue.ACTIVE,
                humanResourceEntity,
                List.of(new PersonalSkill("123456", "java", 10))
        );
        Advertisement updatedAdvertisement = new Advertisement(
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
        when(advertisementRepository.findById(advertisementId)).thenReturn(Optional.of(advertisement));
        when(advertisementRepository.save(advertisement)).thenReturn(updatedAdvertisement);

         advertisementService.changeAdvertisementStatue(statue, advertisementId);


        verify(advertisementRepository).findById(advertisementId);
        verify(advertisementRepository).save(advertisement);
    }

    @Test
    public void testUpdate_whenADVCodeIsUnique_shouldReturnUpdateAdvertisementResponse() {
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "436345",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        UpdateAdvertisementRequest request = new UpdateAdvertisementRequest(
                "345345",
                "53465",
                "obss",
                "2024-01-01",
                "2025-01-01",
                "java",
                List.of("12347")
        );
        LocalDate activationDate = LocalDate.parse(request.activationTime(), DateTimeFormatter.ISO_DATE);
        LocalDate offDate = LocalDate.parse(request.offDate(), DateTimeFormatter.ISO_DATE);
        List<PersonalSkill> personalSkill = List.of(
                new PersonalSkill("12347", "java", 10)
                );
        List<PersonalSkill> updatedPersonalSkill = List.of(
                new PersonalSkill("12347", "java", 10)
                ,new PersonalSkill("123456", "java", 10));
        Advertisement advertisement = new Advertisement(
                "345345",
                "53465",
                "obss",
                "java",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2025, 1, 1),
                AdvertisementStatue.ACTIVE,
                humanResourceEntity,
                List.of(new PersonalSkill("123456", "java", 10))
        );
        Advertisement updatedAdvertisement = new Advertisement(
                request.id(),
                request.advCode(),
                request.title(),
                request.jobDescription(),
                activationDate,
                offDate,
                AdvertisementStatue.ACTIVE,
                humanResourceEntity,
                updatedPersonalSkill
        );
        UpdateAdvertisementResponse expected = new UpdateAdvertisementResponse(
                updatedAdvertisement.getId(),
                updatedAdvertisement.getAdvCode(),
                updatedAdvertisement.getTitle(),
                AdvertisementStatue.ACTIVE,
                updatedAdvertisement.getActivationTime(),
                updatedAdvertisement.getOffDate(),
                updatedAdvertisement.getJobDescription(),
                updatedAdvertisement.getPersonalSkills(),
                updatedAdvertisement.getHumanResourceEntity()
        );
        System.out.println(expected);
        when(advertisementRepository.findById(request.id())).thenReturn(Optional.of(advertisement));
        when(advertisementRepository.findByAdvCode(request.advCode())).thenReturn(Optional.empty());
        when(personalSkillService.findByIdIn(request.personalSkillIds())).thenReturn(personalSkill);
        when(advertisementRepository.save(updatedAdvertisement)).thenReturn(updatedAdvertisement);
        when(advertisementConverter.convertUpdate(updatedAdvertisement)).thenReturn(expected);

        UpdateAdvertisementResponse actual = advertisementService.update(request);

        assertEquals(expected, actual);
    }

    @Test
    public void testUpdate_whenADVCodeIsNotUnique_shouldThrowException() {
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "436345",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        UpdateAdvertisementRequest request = new UpdateAdvertisementRequest(
                "345345",
                "345",
                "obss",
                "2024-01-01",
                "2025-01-01",
                "java",
                List.of("12347")
        );
        Advertisement advertisement = new Advertisement(
                "345345",
                "53465",
                "Obss",
                "java",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2025, 1, 1),
                AdvertisementStatue.ACTIVE,
                humanResourceEntity,
                List.of(new PersonalSkill("123456", "java", 10))
        );

        when(advertisementRepository.findById(request.id())).thenReturn(Optional.of(advertisement));
        when(advertisementRepository.findByAdvCode(request.advCode())).thenThrow(AvdCodeHasBeUniqueException.class);

        assertThrows(AvdCodeHasBeUniqueException.class, () -> advertisementService.update(request));

        verify(advertisementRepository).findById(request.id());
        verify(advertisementRepository).findByAdvCode(request.advCode());
    }

    @Test
    public void testCreateAdvertisement_whenADVCodeUniqueAndHumanResourceExist_shouldReturnCreateAdvertisementResponse() throws InvalidNameException {

        CreateAdvertisementRequest request = new CreateAdvertisementRequest(
                "53465",
                "active",
                "obss",
                "2025-01-01",
                "2024-01-01",
                "java",
                List.of("12347"),
                "cn=mike"
        );
        Name name = new LdapName(request.humanResourceId());
        LocalDate activationDate = LocalDate.parse(request.activationTime(), DateTimeFormatter.ISO_DATE);
        LocalDate offDate = LocalDate.parse(request.offDate(), DateTimeFormatter.ISO_DATE);
        List<PersonalSkill> personalSkill = List.of(
                new PersonalSkill("12347", "java", 10));
        HumanResource humanResource = new HumanResource(
                name,
                "ahmet",
                "dayi",
                "ahmet dayi",
                "255463"
        );

        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "cn=mike",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        Advertisement advertisement = Advertisement
                .builder()
                .advCode(request.advCode())
                .advertisementStatue(AdvertisementStatue.ACTIVE)
                .title(request.title())
                .activationTime(activationDate)
                .offDate(offDate)
                .jobDescription(request.jobDescription())
                .personalSkills(personalSkill)
                .humanResourceEntity(humanResourceEntity)
                .build();

        Advertisement savedAdvertisement = Advertisement
                .builder()
                .id("1546546547")
                .advCode(request.advCode())
                .advertisementStatue(AdvertisementStatue.ACTIVE)
                .title(request.title())
                .activationTime(activationDate)
                .offDate(offDate)
                .jobDescription(request.jobDescription())
                .personalSkills(personalSkill)
                .humanResourceEntity(humanResourceEntity)
                .build();
        CreateAdvertisementResponse expected = new CreateAdvertisementResponse(
                "1546546547",
                request.advCode(),
                request.title(),
                request.jobDescription(),
                activationDate,
                offDate,
                AdvertisementStatue.ACTIVE
        );
        when(humanResourceService.findNameByDn(name)).thenReturn(humanResource);
        when(advertisementRepository.findByAdvCode(request.advCode())).thenReturn(Optional.empty());
        when(advertisementStatueConverter.convert(request.statue())).thenReturn(AdvertisementStatue.ACTIVE);
        when(personalSkillService.findByIdIn(request.personalSkillIdList())).thenReturn(personalSkill);
        when(advertisementRepository.save(advertisement)).thenReturn(savedAdvertisement);
        when(advertisementConverter.convert(savedAdvertisement)).thenReturn(expected);

        CreateAdvertisementResponse actual = advertisementService.create(request);

        assertEquals(expected, actual);

        verify(humanResourceService).findNameByDn(name);
        verify(advertisementRepository).findByAdvCode(request.advCode());
        verify(advertisementStatueConverter).convert(request.statue());
        verify(personalSkillService).findByIdIn(request.personalSkillIdList());
        verify(advertisementConverter).convert(savedAdvertisement);
    }

    @Test
    public void testCreateAdvertisement_whenADVCodeDoesntUniqueAndHumanResourceExist_shouldThrowException() throws InvalidNameException {
        CreateAdvertisementRequest request = new CreateAdvertisementRequest(
                "53465",
                "active",
                "obss",
                "2025-01-01",
                "2024-01-01",
                "java",
                List.of("12347"),
                "cn=mike"
        );
        Name name = new LdapName(request.humanResourceId());
        HumanResource humanResource = new HumanResource(
                name,
                "ahmet",
                "dayi",
                "ahmet dayi",
                "255463"
        );
        when(humanResourceService.findNameByDn(name)).thenReturn(humanResource);
        when(advertisementRepository.findByAdvCode(request.advCode())).thenThrow(AdvertisementHaveAlreadyBeenCreatingException.class);

        assertThrows(AdvertisementHaveAlreadyBeenCreatingException.class, () -> advertisementService.create(request));

        verify(humanResourceService).findNameByDn(name);
        verify(advertisementRepository).findByAdvCode(request.advCode());
    }

    @Test
    public void testCreateAdvertisement_whenADVCodeUniqueAndHumanResourceDoesntExist_shouldThrowException() throws InvalidNameException {
        CreateAdvertisementRequest request = new CreateAdvertisementRequest(
                "53465",
                "active",
                "obss",
                "2025-01-01",
                "2024-01-01",
                "java",
                List.of("12347"),
                "cn=mike"
        );
        Name name = new LdapName(request.humanResourceId());

        when(humanResourceService.findNameByDn(name)).thenThrow(HumanResourceNotFoundException.class);

        assertThrows(HumanResourceNotFoundException.class, () -> advertisementService.create(request));

        verify(humanResourceService).findNameByDn(name);
    }

    @Test
    @DisplayName("find all advertisement and return advertisement list")
    public void testFindAll(){
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "cn=mike",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        List<Advertisement> expected = List.of(new Advertisement(
                "1565",
                "124324",
                "Obss",
                "java",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2025, 1, 1),
                AdvertisementStatue.PASSIVE,
                humanResourceEntity,
                List.of(new PersonalSkill("123456", "java", 10))
        ));

        when(advertisementRepository.findAll()).thenReturn(expected);

        List<Advertisement> actual = advertisementService.findAllAdvertisement();

        assertEquals(expected,actual);

        verify(advertisementRepository).findAll();
    }

    @Test
    public void testFindAdvertisementId_ShouldReturnGtAdvertisementId(){
        String advertisementId = "234";

        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "436345",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        Advertisement advertisement = new Advertisement(
                "234",
                "",
                "Obss",
                "java",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2025, 1, 1),
                AdvertisementStatue.ACTIVE,
                humanResourceEntity,
                List.of(new PersonalSkill("123456", "java", 10))
        );
        GetAdvertisementResponse expected = new GetAdvertisementResponse(
                "234",
                "53465",
                "Obss",
                "java",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2025, 1, 1),
                AdvertisementStatue.ACTIVE,
                humanResourceEntity,
                List.of(new PersonalSkill("123456", "java", 10)
        ));
        when(advertisementRepository.findById(advertisementId)).thenReturn(Optional.of(advertisement));
        when(advertisementConverter.convertGet(advertisement)).thenReturn(expected);

        GetAdvertisementResponse actual = this.advertisementService.findAdvertisementId(advertisementId);

        assertEquals(expected,actual);

        verify(advertisementRepository).findById(advertisementId);
        verify(advertisementConverter).convertGet(advertisement);
    }
}
