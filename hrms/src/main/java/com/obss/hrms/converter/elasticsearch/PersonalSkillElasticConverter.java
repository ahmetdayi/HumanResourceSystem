package com.obss.hrms.converter.elasticsearch;

import com.obss.hrms.entity.PersonalSkill;
import com.obss.hrms.entity.elasticsearch.PersonalSkillElastic;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonalSkillElasticConverter {

    public List<PersonalSkill> convert(List<PersonalSkillElastic> fromList){
        if (fromList == null){
            return null;
        }
        return fromList.stream()
                .map(from -> new PersonalSkill(from.getId(), from.getName(),from.getLevel())).toList();
    }
}
