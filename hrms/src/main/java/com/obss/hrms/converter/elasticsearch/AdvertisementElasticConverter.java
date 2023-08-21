package com.obss.hrms.converter.elasticsearch;

import com.obss.hrms.entity.Advertisement;
import com.obss.hrms.entity.elasticsearch.AdvertisementElastic;
import com.obss.hrms.response.GetAdvertisementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdvertisementElasticConverter {

    private final PersonalSkillElasticConverter personalSkillElasticConverter;

    public List<GetAdvertisementResponse> convert(List<AdvertisementElastic> fromList){
        if (fromList == null){
            return null;
        }
        return fromList.stream()
                .map(from -> new GetAdvertisementResponse(
                        from.getId(),
                        from.getAdvCode(),
                        from.getTitle(),
                        from.getJobDescription(),
                        from.getActivationTime(),
                        from.getOffDate(),
                        from.getAdvertisementStatue(),
                        from.getHumanResourceEntity(),
                        personalSkillElasticConverter.convert(from.getPersonalSkills())
                )).toList();
    }

    public Advertisement convert(AdvertisementElastic from){
        return new Advertisement(from.getId(),
                from.getAdvCode(),
                from.getTitle(),
                from.getJobDescription(),
                from.getActivationTime(),
                from.getOffDate(),
                from.getAdvertisementStatue(),
                from.getHumanResourceEntity(),
                personalSkillElasticConverter.convert(from.getPersonalSkills()));
    }
}
