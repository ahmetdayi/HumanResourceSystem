package com.obss.hrms.service;

import com.obss.hrms.converter.AdvertisementConverter;
import com.obss.hrms.converter.AdvertisementStatueConverter;
import com.obss.hrms.entity.*;
import com.obss.hrms.exception.AdvertisementHaveAlreadyBeenCreatingException;
import com.obss.hrms.exception.AdvertisementNotFoundException;
import com.obss.hrms.exception.AvdCodeHasBeUniqueException;
import com.obss.hrms.exception.Constant;
import com.obss.hrms.repository.AdvertisementRepository;
import com.obss.hrms.request.CreateAdvertisementRequest;
import com.obss.hrms.request.UpdateAdvertisementRequest;
import com.obss.hrms.response.CreateAdvertisementResponse;
import com.obss.hrms.response.GetAdvertisementResponse;
import com.obss.hrms.response.UpdateAdvertisementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.ldap.LdapName;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final HumanResourceService humanResourceService;
    private final PersonalSkillService personalSkillService;

    private final AdvertisementConverter advertisementConverter;
    private final AdvertisementStatueConverter advertisementStatueConverter;

    public List<GetAdvertisementResponse> findAll() {
        return advertisementConverter.convertGet(advertisementRepository.findAll());
    }

    public CreateAdvertisementResponse create(CreateAdvertisementRequest request) throws InvalidNameException {
        HumanResourceEntity humanResourceEntity = getHumanResourceEntity(request);
        LocalDate activationDate = LocalDate.parse(request.activationTime(), DateTimeFormatter.ISO_DATE);
        LocalDate offDate = LocalDate.parse(request.offDate(), DateTimeFormatter.ISO_DATE);
        if (isAdvCodeUniqControl(request.advCode())) {
            throw new AdvertisementHaveAlreadyBeenCreatingException(Constant.ADVERTISEMENT_HAVE_ALREADY_CREATING);
        }
        Advertisement advertisement = Advertisement
                .builder()
                .advCode(request.advCode())
                .advertisementStatue(advertisementStatueConverter.convert(request.statue()))
                .title(request.title())
                .activationTime(activationDate)
                .offDate(offDate)
                .jobDescription(request.jobDescription())
                .personalSkills(personalSkillService.findByIdIn(request.personalSkillIdList()))
                .humanResourceEntity(humanResourceEntity)
                .build();
        return advertisementConverter.convert(advertisementRepository.save(advertisement));
    }

    public void changeAdvertisementStatue(boolean statue, String advertisementId) {
        Advertisement advertisement = findById(advertisementId);

        AdvertisementStatue newStatue = statue ? AdvertisementStatue.ACTIVE : AdvertisementStatue.PASSIVE;
        advertisement.setAdvertisementStatue(newStatue);
        advertisementRepository.save(advertisement);


    }

    public UpdateAdvertisementResponse update(UpdateAdvertisementRequest request) {
        LocalDate activationDate = null;
        LocalDate offDate = null;
        List<PersonalSkill> personalSkills = null;
        List<PersonalSkill> newList = null;
        if (!request.activationTime().trim().equals("") || !request.offDate().trim().equals("")) {
            activationDate = LocalDate.parse(request.activationTime(), DateTimeFormatter.ISO_DATE);
            offDate = LocalDate.parse(request.offDate(), DateTimeFormatter.ISO_DATE);
        }

        Advertisement advertisement = findById(request.id());
        if (isAdvCodeUniqControl(request.advCode())) {
            throw new AvdCodeHasBeUniqueException(Constant.AVD_CODE_HAS_BE_UNIQUE);
        }

        if (request.personalSkillIds().size() != 0 && request.personalSkillIds() != null) {
            newList = new ArrayList<>(advertisement.getPersonalSkills());
            personalSkills = personalSkillService.findByIdIn(request.personalSkillIds());
            newList.addAll(personalSkills);
        }

        Advertisement updatedAdvertisement = Advertisement.builder()
                .id(advertisement.getId())
                .advCode(request.advCode().trim().equals("") ? advertisement.getAdvCode() : request.advCode())
                .title(request.title().trim().equals("") ? advertisement.getTitle() : request.title())
                .activationTime(activationDate == null ? advertisement.getActivationTime() : activationDate)
                .offDate(offDate == null ? advertisement.getOffDate() : offDate)
                .jobDescription(request.jobDescription().trim().equals("") ? advertisement.getJobDescription() : request.jobDescription())
                .personalSkills(newList)
                .advertisementStatue(advertisement.getAdvertisementStatue())
                .humanResourceEntity(advertisement.getHumanResourceEntity())
                .build();

        return advertisementConverter.convertUpdate(advertisementRepository.save(updatedAdvertisement));
    }

    public GetAdvertisementResponse findAdvertisementId(String advertisementId) {
        return advertisementConverter.convertGet(findById(advertisementId));
    }

    protected Advertisement findById(String advertisementId) {
        return advertisementRepository
                .findById(advertisementId)
                .orElseThrow(() -> new AdvertisementNotFoundException(Constant.ADVERTISEMENT_NOT_FOUND));
    }

    public List<Advertisement> findAllAdvertisement() {
        return advertisementRepository.findAll();
    }

    private boolean isAdvCodeUniqControl(String advCode) {
        Optional<Advertisement> byAdvCode = advertisementRepository.findByAdvCode(advCode);
        return byAdvCode.isPresent();

    }

    private HumanResourceEntity getHumanResourceEntity(CreateAdvertisementRequest request) throws InvalidNameException {
        Name humanResourceName = new LdapName(request.humanResourceId());
        HumanResource humanResource = humanResourceService.findNameByDn(humanResourceName);
        return new HumanResourceEntity(
                humanResource.getDn().toString(),
                humanResource.getFullName(),
                humanResource.getLastName(),
                humanResource.getDisplayName());
    }

    @Scheduled(fixedRate = 60000) // Her dakika çalışacak şekilde ayarlanmış zamanlayıcı
    private void checkAdvertisementActivationAndOffDate() {
        List<Advertisement> advertisementList = advertisementRepository.findAll(); // Tüm ilanları al

        LocalDate now = LocalDate.now(); // Şu anki tarih

        advertisementList.stream()
                .filter(ilan -> ilan.getOffDate() != null && ilan.getOffDate().isAfter(now))
                .forEach(ilan -> {
                    ilan.setAdvertisementStatue(AdvertisementStatue.PASSIVE); // Passive hale getir
                    advertisementRepository.save(ilan); // Güncellemeleri kaydet
                });
        advertisementList.stream()
                .filter(ilan -> ilan.getActivationTime() != null && ilan.getActivationTime().isAfter(now))
                .forEach(ilan -> {
                    ilan.setAdvertisementStatue(AdvertisementStatue.ACTIVE); // Active hale getir
                    advertisementRepository.save(ilan); // Güncellemeleri kaydet
                });
    }

}
