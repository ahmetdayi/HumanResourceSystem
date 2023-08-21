package com.obss.hrms.repository;

import com.obss.hrms.entity.BlackList;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BlackListRepository extends MongoRepository<BlackList,String> {
    Optional<BlackList> findByJobSeeker_Id(String jobSeekerId);

}
