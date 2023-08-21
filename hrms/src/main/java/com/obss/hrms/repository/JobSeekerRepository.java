package com.obss.hrms.repository;

import com.obss.hrms.entity.JobSeeker;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface JobSeekerRepository extends MongoRepository<JobSeeker,String> {

    Optional<JobSeeker> findByEmail(String email);
}
