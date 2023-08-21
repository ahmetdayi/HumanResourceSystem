package com.obss.hrms.converter;

import com.obss.hrms.entity.JobSeeker;
import com.obss.hrms.response.GetJobSeekerResponse;
import com.obss.hrms.response.UpdateJobSeekerResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobSeekerConverter {

    public UpdateJobSeekerResponse convert(JobSeeker from){
        return new UpdateJobSeekerResponse(
                from.getId(),
                from.getFirstname(),
                from.getLastName(),
                from.getEmail(),
                from.getBirthDay(),
                from.getDescription(),
                from.getInBlackList(),
                from.getPersonalSkillList()
        );
    }

    public GetJobSeekerResponse convertGet(JobSeeker from){
        return new GetJobSeekerResponse(
                from.getId(),
                from.getFirstname(),
                from.getLastName(),
                from.getEmail(),
                from.getBirthDay(),
                from.getDescription(),
                from.getInBlackList(),
                from.getPersonalSkillList()
        );
    }
    public List<GetJobSeekerResponse> convertGet(List<JobSeeker> fromList){
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
                        from.getPersonalSkillList()
                )).toList();
    }


}
