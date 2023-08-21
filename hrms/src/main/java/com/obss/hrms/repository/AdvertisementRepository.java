package com.obss.hrms.repository;

import com.obss.hrms.entity.Advertisement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Optional;
@EnableMongoRepositories
public interface AdvertisementRepository extends MongoRepository<Advertisement,String> {
    Optional<Advertisement> findByAdvCode(String advCode);

}
