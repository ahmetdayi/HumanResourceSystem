package com.obss.hrms.repository;

import com.obss.hrms.entity.PersonalSkill;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PersonalSkillRepository extends MongoRepository<PersonalSkill,String> {
    List<PersonalSkill> findByIdIn(List<String> skillIdList);
}
