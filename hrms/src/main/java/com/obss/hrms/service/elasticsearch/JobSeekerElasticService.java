package com.obss.hrms.service.elasticsearch;

import com.obss.hrms.converter.elasticsearch.JobSeekerElasticConverter;
import com.obss.hrms.entity.elasticsearch.JobSeekerElastic;
import com.obss.hrms.repository.elasticsearch.JobSeekerElasticRepository;
import com.obss.hrms.response.GetJobSeekerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class JobSeekerElasticService {
    private final JobSeekerElasticRepository jobSeekerElasticRepository;

    private final JobSeekerElasticConverter jobSeekerElasticConverter;

    public List<GetJobSeekerResponse> search(String query){
        return jobSeekerElasticConverter.convert(jobSeekerElasticRepository
                .findByFirstnameLikeOrLastNameLikeOrDescriptionLikeOrPersonalSkillList_nameLike(query,query,query,query));
    }
    public void saveAll(List<JobSeekerElastic> jobSeekerElastic){
        jobSeekerElasticRepository.saveAll(jobSeekerElastic);
    }

    public List<JobSeekerElastic> findAll(){
        return StreamSupport.stream(jobSeekerElasticRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
