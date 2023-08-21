package com.obss.hrms.repository.elasticsearch;

import com.obss.hrms.entity.elasticsearch.AdvertisementElastic;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AdvertisementElasticRepository extends ElasticsearchRepository<AdvertisementElastic, String> {


    List<AdvertisementElastic> findByAdvCodeLikeOrTitleLikeOrJobDescriptionLike(String advCode,String title,String jobDescription);
}
