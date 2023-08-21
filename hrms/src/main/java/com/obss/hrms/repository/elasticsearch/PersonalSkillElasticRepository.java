package com.obss.hrms.repository.elasticsearch;


import com.obss.hrms.entity.elasticsearch.PersonalSkillElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PersonalSkillElasticRepository extends ElasticsearchRepository<PersonalSkillElastic, String> {
}
