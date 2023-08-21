package com.obss.hrms.service;

import com.obss.hrms.converter.PersonalSkillConverter;
import com.obss.hrms.entity.PersonalSkill;
import com.obss.hrms.exception.Constant;
import com.obss.hrms.exception.PersonalSkillNotFoundException;
import com.obss.hrms.repository.PersonalSkillRepository;
import com.obss.hrms.request.CreatePersonalSkillRequest;
import com.obss.hrms.response.CreatePersonalSkillResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonalSkillService {

    private final PersonalSkillRepository personalSkillRepository;
    private final PersonalSkillConverter personalSkillConverter;

    public CreatePersonalSkillResponse create(CreatePersonalSkillRequest request){
        PersonalSkill personalSkill = new PersonalSkill(request.name(), request.level());
        return personalSkillConverter.convert(personalSkillRepository.save(personalSkill));
    }

    public void delete(String personalSkillId){
        personalSkillRepository.delete(findById(personalSkillId));
    }

    protected List<PersonalSkill> findByIdIn(List<String> personalSkillIdList){
        return personalSkillRepository.findByIdIn(personalSkillIdList);
    }
    protected PersonalSkill findById(String personalSkillId){
        return personalSkillRepository
                .findById(personalSkillId)
                .orElseThrow(()->new PersonalSkillNotFoundException(Constant.PERSONAL_SKILL_NOT_FOUND));
    }
    public List<PersonalSkill> findAllPersonalSkill(){
        return personalSkillRepository.findAll();
    }

}
