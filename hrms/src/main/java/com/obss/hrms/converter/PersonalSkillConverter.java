package com.obss.hrms.converter;

import com.obss.hrms.entity.PersonalSkill;
import com.obss.hrms.response.CreatePersonalSkillResponse;
import org.springframework.stereotype.Component;

@Component
public class PersonalSkillConverter {

    public CreatePersonalSkillResponse convert(PersonalSkill from){
        return new CreatePersonalSkillResponse(
                from.getId(),
                from.getName(),
                from.getLevel()
        );
    }
}
