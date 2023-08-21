package com.obss.hrms.repository;


import com.obss.hrms.entity.ApplyAdvertisement;
import com.obss.hrms.entity.ApplyAdvertisementStatue;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ApplyAdvertisementRepository extends MongoRepository<ApplyAdvertisement,String> {

    Optional<List<ApplyAdvertisement>> findByJobSeeker_Id(String jobSeekerId);
    Optional<List<ApplyAdvertisement>> findByAdvertisement_Id(String advertisementId);
    List<ApplyAdvertisement> findByApplyAdvertisementStatue(ApplyAdvertisementStatue applyAdvertisementStatue);
    List<ApplyAdvertisement> findByAdvertisement_AdvCode(String advCode);

    ApplyAdvertisement findByAdvertisement_IdAndJobSeeker_Id(String advertisementId,String jobSeekerId);

}
