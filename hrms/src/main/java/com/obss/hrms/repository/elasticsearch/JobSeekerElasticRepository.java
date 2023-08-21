package com.obss.hrms.repository.elasticsearch;

import com.obss.hrms.entity.elasticsearch.JobSeekerElastic;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobSeekerElasticRepository extends ElasticsearchRepository<JobSeekerElastic, String> {
List<JobSeekerElastic> findByFirstnameLikeOrLastNameLikeOrDescriptionLikeOrPersonalSkillList_nameLike(String firstname,String lastname,String description,String personalSkillName);
}
