package com.obss.hrms.converter.elasticsearch;

import com.obss.hrms.entity.JobSeeker;
import com.obss.hrms.entity.elasticsearch.JobSeekerElastic;
import com.obss.hrms.response.GetJobSeekerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JobSeekerElasticConverter {

    private final PersonalSkillElasticConverter personalSkillElasticConverter;

    public List<GetJobSeekerResponse> convert(List<JobSeekerElastic> fromList){
        if (fromList == null){
            return null;
        }
        return fromList.stream()
                .map(from -> new GetJobSeekerResponse(
                        from.getId(),
                        from.getFirstname(),
                        from.getLastName(),
                        from.getEmail(),
                        from.getBirthDay(),
                        from.getDescription(),
                        from.getInBlackList(),
                        personalSkillElasticConverter.convert(from.getPersonalSkillList())
                )).toList();
    }

    public JobSeeker convert(JobSeekerElastic from){
        return new JobSeeker(
                from.getId(),
                from.getFirstname(),
                from.getLastName(),
                from.getBirthDay(),
                from.getDescription(),
                from.getInBlackList(),
                from.getRole(),
                personalSkillElasticConverter.convert(from.getPersonalSkillList())
        );
    }
}
