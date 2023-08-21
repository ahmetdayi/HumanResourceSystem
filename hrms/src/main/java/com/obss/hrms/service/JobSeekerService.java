package com.obss.hrms.service;

import com.obss.hrms.converter.JobSeekerConverter;
import com.obss.hrms.entity.JobSeeker;
import com.obss.hrms.entity.PersonalSkill;
import com.obss.hrms.exception.Constant;
import com.obss.hrms.exception.JobSeekerNotFoundException;
import com.obss.hrms.repository.JobSeekerRepository;
import com.obss.hrms.request.AddPersonalSkillInJobSeekerRequest;
import com.obss.hrms.request.UpdateJobSeekerRequest;
import com.obss.hrms.response.GetJobSeekerResponse;
import com.obss.hrms.response.UpdateJobSeekerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class JobSeekerService {

    private final JobSeekerRepository jobSeekerRepository;
    private final JobSeekerConverter jobSeekerConverter;
    public final PersonalSkillService personalSkillService;


    public void save(JobSeeker jobSeeker) {
        jobSeekerRepository.save(jobSeeker);
    }

    public UpdateJobSeekerResponse update(UpdateJobSeekerRequest request) {
        LocalDate birthDay = null;
        if (!request.birthDay().trim().equals("")){
            birthDay = LocalDate.parse(request.birthDay(), DateTimeFormatter.ISO_DATE);
        }
        JobSeeker byId = findById(request.id());

        byId.setFirstname(request.firstname().trim().equals("") ? byId.getFirstname() : request.firstname());
        byId.setLastName(request.lastName().trim().equals("") ? byId.getLastName() : request.lastName());
        byId.setEmail(byId.getEmail());
        byId.setBirthDay(birthDay == null ? byId.getBirthDay() : birthDay);
        byId.setDescription(request.description().trim().equals("") ? byId.getDescription() : request.description());
        byId.setPersonalSkillList(byId.getPersonalSkillList());
        return jobSeekerConverter.convert(jobSeekerRepository.save(byId));

    }

    public GetJobSeekerResponse findJobSeekerById(String jobSeekerId) {
        return jobSeekerConverter.convertGet(findById(jobSeekerId));
    }

    public List<GetJobSeekerResponse> findAll() {
        return jobSeekerConverter.convertGet(jobSeekerRepository.findAll());
    }

    public GetJobSeekerResponse addPersonalSkill(AddPersonalSkillInJobSeekerRequest request) {
        List<PersonalSkill> personalSkills = personalSkillService.findByIdIn(request.personalSkillIdList());
        JobSeeker jobSeeker = findById(request.jobSeekerId());

        List<PersonalSkill> jobSeekerHasBeenPossessingAbility =
                isJobSeekerHasBeenPossessingAbility(jobSeeker.getPersonalSkillList(), personalSkills);

        jobSeeker.setPersonalSkillList(jobSeekerHasBeenPossessingAbility);

        JobSeeker save = jobSeekerRepository.save(jobSeeker);

        return jobSeekerConverter.convertGet(save);
    }


    public JobSeeker findById(String jobSeekerId) {
        return jobSeekerRepository
                .findById(jobSeekerId)
                .orElseThrow(() -> new JobSeekerNotFoundException(Constant.JOB_SEEKER_NOT_FOUND));
    }

    public List<JobSeeker> findAllJobSeeker(){
        return jobSeekerRepository.findAll();
    }

    protected void updateInBlackList(JobSeeker jobSeeker, boolean value) {
        jobSeeker.setInBlackList(value);
        jobSeekerRepository.save(jobSeeker);
    }

    private List<PersonalSkill> isJobSeekerHasBeenPossessingAbility(List<PersonalSkill> jobSeekerPL, List<PersonalSkill> requestPL) {
        if (jobSeekerPL == null){
            jobSeekerPL = new ArrayList<>();
        }

        for (PersonalSkill skillFromList2 : requestPL) {
            boolean isFoundInList1 = false;
            for (PersonalSkill skillFromList1 : jobSeekerPL) {
                if (skillFromList1.getName().trim().equalsIgnoreCase(skillFromList2.getName())) {
                    isFoundInList1 = true;

                    if (skillFromList2.getLevel() > skillFromList1.getLevel()) {
                        skillFromList1.setId(skillFromList2.getId());
                        skillFromList1.setLevel(skillFromList2.getLevel());

                    }
                        break;
                }
            }
            if (!isFoundInList1) {
                jobSeekerPL.add(skillFromList2);
            }

        }
        return jobSeekerPL;
    }
}
