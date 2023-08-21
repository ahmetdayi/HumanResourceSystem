package com.obss.hrms.service;


import com.obss.hrms.converter.ApplyAdvertisementConverter;
import com.obss.hrms.converter.ApplyAdvertisementStatueConverter;
import com.obss.hrms.entity.*;
import com.obss.hrms.exception.*;
import com.obss.hrms.repository.ApplyAdvertisementRepository;
import com.obss.hrms.request.CreateApplyAdvertisementRequest;
import com.obss.hrms.request.SendMailRequest;
import com.obss.hrms.response.CreateApplyAdvertisementResponse;
import com.obss.hrms.response.GetApplyAdvertisementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplyAdvertisementService {

    private final ApplyAdvertisementRepository applyAdvertisementRepository;
    private final JobSeekerService jobSeekerService;
    private final AdvertisementService advertisementService;

    private final MailService mailService;
    private final ApplyAdvertisementStatueConverter applyAdvertisementStatueConverter;
    private final ApplyAdvertisementConverter applyAdvertisementConverter;


    public CreateApplyAdvertisementResponse create(CreateApplyAdvertisementRequest request) {
        Advertisement advertisement = advertisementService.findById(request.advertisementId());
        JobSeeker jobSeeker = jobSeekerService.findById(request.jobSeekerId());
        LocalDate applyDate = LocalDate.parse(LocalDate.now().toString(), DateTimeFormatter.ISO_DATE);

        List<ApplyAdvertisement> byJobSeeker_id = findByJobSeeker_Id(request.jobSeekerId());

        //kara lıstede olup olmadıgını kontrol edıyorum eger kara lıstedeyse basvuru yapamıyor.
        if (jobSeeker.getInBlackList()) {
            throw new JobSeekerIsAlreadyOccupyingBlackListException(
                    Constant.JOB_SEEKER_HAVE_ALREADY_BEEN_OCCUPYING_BLACK_LIST_REJECT_APPLICATION);
        }

        //daha once bu ılana basvurmus mu diye bakıyoruz
        boolean isApply = byJobSeeker_id.stream().anyMatch(applyAdvertisement ->
                applyAdvertisement.getAdvertisement().getId().equals(request.advertisementId()));

        if (!isApply) {
            if (!advertisement.getAdvertisementStatue().toString().equals("PASSIVE")){
                ApplyAdvertisement applyAdvertisement =
                        new ApplyAdvertisement(applyDate, applyAdvertisementStatueConverter
                                .convert(request.applyAdvertisementStatue()), jobSeeker, advertisement);
                return applyAdvertisementConverter.convert(applyAdvertisementRepository.save(applyAdvertisement));
            }else {
                throw new AdvertisementPassiveException(Constant.ADVERTISEMENT_PASSIVE);
            }

        } else {
            throw new JobSeekerIsAlreadyApplyingAdvertisementException(
                    Constant.JOB_SEEKER_HAVE_ALREADY_BEEN_APPLYING_ADVERTISEMENT);
        }
    }

    public List<GetApplyAdvertisementResponse> findByAdvertisementId(String advertisementId) {
        return applyAdvertisementConverter
                .convertGet(applyAdvertisementRepository
                        .findByAdvertisement_Id(advertisementId)
                        .orElseThrow(
                                () -> new ApplicantNotFoundThisAdvertisementException(
                                        Constant.APPLICANT_NOT_FOUND_THIS_ADVERTISEMENT)));
    }

    public List<GetApplyAdvertisementResponse> findByJobSeekerId(String jobSeekerId) {
        return applyAdvertisementConverter
                .convertGet(findByJobSeeker_Id(jobSeekerId));
    }

    public List<GetApplyAdvertisementResponse> findAll() {
        return applyAdvertisementConverter.convertGet(applyAdvertisementRepository.findAll());
    }

    public void updateApplyAdvertisementStatue(String statue, String applyAdvertisementId) {

        ApplyAdvertisement applyAdvertisement = findById(applyAdvertisementId);


        ApplyAdvertisementStatue newStatue = switch (statue.toLowerCase()) {
            case "reject" -> ApplyAdvertisementStatue.REJECT;
            case "ok" -> ApplyAdvertisementStatue.OK;
            case "processing" -> ApplyAdvertisementStatue.PROCESSING;
            default -> throw new ApplyAdvertisementStatueIsWrongException(Constant.APPLY_ADVERTISEMENT_STATUE_IS_WRONG);
        };

        applyAdvertisement.setApplyAdvertisementStatue(newStatue);
        applyAdvertisementRepository.save(applyAdvertisement);
        sendMail(applyAdvertisement, newStatue);


    }

    public List<GetApplyAdvertisementResponse> filter(String statue) {
        ApplyAdvertisementStatue applyAdvertisementStatue = applyAdvertisementStatueConverter.convert(statue); // İlgili metotla string statüyü AdvertisementStatue'ye çevirin

        List<ApplyAdvertisement> applyAdvertisements = applyAdvertisementRepository.findByApplyAdvertisementStatue(applyAdvertisementStatue);
        return applyAdvertisementConverter.convertGet(applyAdvertisements);

    }

    public List<GetApplyAdvertisementResponse> findAllApplyAdvertisementsSortedByMatchCount() {
        // Boş bir applyAdvertisements listesi oluşturulur.
        List<ApplyAdvertisement> applyAdvertisements = new ArrayList<>();

// calculateSkillMatches metodu çağrılarak Advertisement ve JobSeeker'ları eşleştiren bir Map elde edilir.
        Map<Advertisement, Map<JobSeeker, Integer>> sortedApplyAdvertisement = calculateSkillMatches();

// sortedApplyAdvertisement üzerinde forEach ile Advertisement ve JobSeeker eşleşmeleri döngüye alınır.
        sortedApplyAdvertisement.forEach((key, jobSeekerMap) -> jobSeekerMap.entrySet().stream()
                // Eşleşme sayısına göre JobSeeker'ları sıralar ve döngüye alır.
                .sorted(Map.Entry.<JobSeeker, Integer>comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .forEach(jobSeeker -> {
                    // Advertisement ve JobSeeker ID'lerine göre ilgili ApplyAdvertisement nesnesi aranır.
                    ApplyAdvertisement applyAdvertisement = applyAdvertisementRepository
                            .findByAdvertisement_IdAndJobSeeker_Id(key.getId(), jobSeeker.getId());

                    // Eğer ApplyAdvertisement bulunursa, applyAdvertisements listesine eklenir.
                    if (applyAdvertisement != null) {
                        applyAdvertisements.add(applyAdvertisement);
                    }
                }));


        return applyAdvertisementConverter.convertGet(applyAdvertisements);
    }

    public List<ApplyAdvertisement> findAllApplyAdvertisement() {

        return applyAdvertisementRepository.findAll();
    }

    public GetApplyAdvertisementResponse findByApplyAdvertisementId(String applyAdvertisementId) {
        return applyAdvertisementConverter.convertGet(findById(applyAdvertisementId));
    }

    protected List<ApplyAdvertisement> findByJobSeeker_Id(String jobSeekerId) {
        return applyAdvertisementRepository
                .findByJobSeeker_Id(jobSeekerId)
                .orElseThrow(
                        () -> new ApplicantNotFoundThisAdvertisementException(
                                Constant.APPLICANT_NOT_FOUND_THIS_ADVERTISEMENT));
    }

    protected ApplyAdvertisement findById(String applyAdvertisementId) {
        return applyAdvertisementRepository
                .findById(applyAdvertisementId)
                .orElseThrow(() -> new AdvertisementApplicationNotFoundException(
                        Constant.ADVERTISEMENT_APPLICATION_NOT_FOUND));
    }

    protected List<ApplyAdvertisement> findByAdvCode(String advCode) {
        return applyAdvertisementRepository.findByAdvertisement_AdvCode(advCode);
    }

    private Map<Advertisement, Map<JobSeeker, Integer>> calculateSkillMatches() {
        List<ApplyAdvertisement> applyAdvertisements = applyAdvertisementRepository.findAll();
        // Advertisement ve JobSeeker'ları eşleştiren bir Map oluşturmak için kullanılacak sonuç Map'ı.
        return applyAdvertisements.stream().collect(Collectors.groupingBy( //// applyAdvertisements listesini bir Stream'e dönüştürür.
                ApplyAdvertisement::getAdvertisement, //// Advertisement'a göre gruplandırma yapar.
                Collectors.toMap( //// Her Advertisement için JobSeeker'ları bir eşleşmiş Map'e dönüştürür.
                        ApplyAdvertisement::getJobSeeker, // // JobSeeker'a göre eşleştirmeyi yapar.
                        applyAdvertisement -> { //// Lambda ifadesi: JobSeeker'ın becerileri ile
                            // Advertisement'ın becerilerini karşılaştırır ve eşleşme sayısını hesaplar.
                            List<PersonalSkill> advertisementSkills =
                                    // Advertisement ve JobSeeker nesnelerinden kişisel beceriler alınır.
                                    applyAdvertisement.getAdvertisement().getPersonalSkills();

                            List<PersonalSkill> jobSeekerSkills = applyAdvertisement.getJobSeeker().getPersonalSkillList();

                            return (int) advertisementSkills.stream()
                                    // Eşleşen becerileri saymak için matchCount değişkeni oluşturulur.
                                    .flatMap(advertisementSkill -> jobSeekerSkills.stream().filter(jobSeekerSkill ->
                                                    // Advertisement ve JobSeeker becerilerini
                                                    // karşılaştırır ve isimleri eşit olanları bulur.
                                                    advertisementSkill.getName().equalsIgnoreCase(jobSeekerSkill.getName()))

                                            .filter(skill -> skill
                                                    // Seviyeleri eşleşenleri filtreler ve matchCount'u artırır.
                                                    .getLevel().equals(advertisementSkill.getLevel()))).count();// Son olarak, eşleşen beceri sayısını döndürür.
                        })));
    }

    private void sendMail(ApplyAdvertisement applyAdvertisement, ApplyAdvertisementStatue newStatue) {
        SendMailRequest sendMailRequest = new SendMailRequest(
                applyAdvertisement.getJobSeeker().getEmail(),
                ("Advertisement " + applyAdvertisement.getAdvertisement().getAdvCode() + " application statue is " + newStatue),
                "Advertisement Application's statue changed"
        );
        mailService.sendMail(sendMailRequest);
    }


}
