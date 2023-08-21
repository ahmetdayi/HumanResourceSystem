package com.obss.hrms.service.elasticsearch;

import com.obss.hrms.entity.elasticsearch.PersonalSkillElastic;
import com.obss.hrms.repository.elasticsearch.PersonalSkillElasticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class PersonalSkillElasticService {

    private final PersonalSkillElasticRepository personalSkillElasticRepository;

    public void saveAll(List<PersonalSkillElastic> personalSkillElastics){
        personalSkillElasticRepository.saveAll(personalSkillElastics);
    }

    public List<PersonalSkillElastic> findAll(){
        return StreamSupport.stream(personalSkillElasticRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
