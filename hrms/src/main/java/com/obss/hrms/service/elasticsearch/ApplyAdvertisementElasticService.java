package com.obss.hrms.service.elasticsearch;

import com.obss.hrms.converter.elasticsearch.ApplyAdvertisementElasticConverter;
import com.obss.hrms.entity.elasticsearch.ApplyAdvertisementElastic;

import com.obss.hrms.repository.elasticsearch.ApplyAdvertisementElasticRepository;
import com.obss.hrms.response.GetApplyAdvertisementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ApplyAdvertisementElasticService {

    private final ApplyAdvertisementElasticRepository applyAdvertisementElasticRepository;

    private final ApplyAdvertisementElasticConverter applyAdvertisementElasticConverter;

    public List<ApplyAdvertisementElastic> searchApplyAdvertisement(String query){
        return applyAdvertisementElasticRepository.findByJobSeeker_FirstnameLikeOrJobSeeker_LastNameLikeOrJobSeeker_DescriptionLikeOrJobSeeker_PersonalSkillList_nameLikeOrAdvertisement_AdvCodeLikeOrAdvertisement_TitleLikeOrAdvertisement_JobDescriptionLikeOrAdvertisement_PersonalSkills_nameLike(query,query,query,query,query,query,query,query);
    }
    public void saveAll(List<ApplyAdvertisementElastic> applyAdvertisementElastics){
        applyAdvertisementElasticRepository.saveAll(applyAdvertisementElastics);
    }

    public List<ApplyAdvertisementElastic> findAll(){
        return StreamSupport.stream(applyAdvertisementElasticRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
