package com.obss.hrms.converter.elasticsearch;

import com.obss.hrms.entity.Advertisement;
import com.obss.hrms.entity.ApplyAdvertisement;
import com.obss.hrms.entity.JobSeeker;
import com.obss.hrms.entity.PersonalSkill;
import com.obss.hrms.entity.elasticsearch.AdvertisementElastic;
import com.obss.hrms.entity.elasticsearch.ApplyAdvertisementElastic;
import com.obss.hrms.entity.elasticsearch.JobSeekerElastic;
import com.obss.hrms.entity.elasticsearch.PersonalSkillElastic;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongoToElasticConverter {

    public List<JobSeekerElastic> convertElasticJS(List<JobSeeker> fromList){
        if (fromList == null){
            return null;
        }
        return fromList.stream()
                .map(from -> new JobSeekerElastic(
                        from.getId(),
                        from.getFirstname(),
                        from.getLastName(),
                        from.getEmail(),
                        from.getBirthDay(),
                        from.getDescription(),
                        from.getInBlackList(),
                        from.getRole(),
                        convertElasticPS(from.getPersonalSkillList())
                )).toList();
    }
    public JobSeekerElastic convertElasticJS(JobSeeker from){
        return  new JobSeekerElastic(
                        from.getId(),
                        from.getFirstname(),
                        from.getLastName(),
                        from.getEmail(),
                        from.getBirthDay(),
                        from.getDescription(),
                        from.getInBlackList(),
                        from.getRole(),
                        convertElasticPS(from.getPersonalSkillList())
                );
    }
    public List<PersonalSkillElastic> convertElasticPS(List<PersonalSkill> fromList){
        if (fromList == null){
            return null;
        }
        return fromList.stream()
                .map(from -> new PersonalSkillElastic(
                        from.getId(),
                        from.getName(),
                        from.getLevel()
                )).toList();
    }

    public List<AdvertisementElastic> convertElasticAD(List<Advertisement> fromList){
        if (fromList == null){
            return null;
        }
        return fromList.stream()
                .map(from -> new AdvertisementElastic(
                        from.getId(),
                        from.getAdvCode(),
                        from.getTitle(),
                        from.getJobDescription(),
                        from.getActivationTime(),
                        from.getOffDate(),
                        from.getAdvertisementStatue(),
                        from.getHumanResourceEntity(),
                        convertElasticPS(from.getPersonalSkills())
                )).toList();
    }
    public AdvertisementElastic convertElasticAD(Advertisement from){

        return  new AdvertisementElastic(
                        from.getId(),
                        from.getAdvCode(),
                        from.getTitle(),
                        from.getJobDescription(),
                        from.getActivationTime(),
                        from.getOffDate(),
                        from.getAdvertisementStatue(),
                        from.getHumanResourceEntity(),
                        convertElasticPS(from.getPersonalSkills())
                );
    }
    public List<ApplyAdvertisementElastic> convertElasticAA(List<ApplyAdvertisement> fromList){
        if (fromList == null){
            return null;
        }
        return fromList.stream()
                .map(from -> new ApplyAdvertisementElastic(
                        from.getId(),
                        from.getApplyDate(),
                        from.getApplyAdvertisementStatue(),
                        convertElasticJS(from.getJobSeeker()),
                        convertElasticAD(from.getAdvertisement())
                )).toList();
    }
}
