package com.obss.hrms.repository.elasticsearch;

import com.obss.hrms.entity.elasticsearch.ApplyAdvertisementElastic;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplyAdvertisementElasticRepository extends ElasticsearchRepository<ApplyAdvertisementElastic, String> {
  List<ApplyAdvertisementElastic> findByJobSeeker_FirstnameLikeOrJobSeeker_LastNameLikeOrJobSeeker_DescriptionLikeOrJobSeeker_PersonalSkillList_nameLikeOrAdvertisement_AdvCodeLikeOrAdvertisement_TitleLikeOrAdvertisement_JobDescriptionLikeOrAdvertisement_PersonalSkills_nameLike(String firstname,String lastname,String description,String personalSkillName,String advCode,String title,String jobDescription,String personalSkillNameAdv);


}
