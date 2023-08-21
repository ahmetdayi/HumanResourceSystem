package com.obss.hrms.service.elasticsearch;

import com.obss.hrms.converter.elasticsearch.AdvertisementElasticConverter;
import com.obss.hrms.entity.elasticsearch.AdvertisementElastic;

import com.obss.hrms.repository.elasticsearch.AdvertisementElasticRepository;
import com.obss.hrms.response.GetAdvertisementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class AdvertisementElasticService {

    private final AdvertisementElasticRepository advertisementElasticRepository;

    private final AdvertisementElasticConverter advertisementElasticConverter;

    public List<AdvertisementElastic> searchAdvertisement(String query){
        return advertisementElasticRepository.findByAdvCodeLikeOrTitleLikeOrJobDescriptionLike(query,query,query);
    }
    public void saveAll(List<AdvertisementElastic> advertisementElastics){
        advertisementElasticRepository.saveAll(advertisementElastics);
    }
    public List<AdvertisementElastic> findAll(){

        return StreamSupport.stream(advertisementElasticRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
