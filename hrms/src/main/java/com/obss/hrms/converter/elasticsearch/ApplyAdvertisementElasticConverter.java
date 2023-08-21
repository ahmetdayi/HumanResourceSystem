package com.obss.hrms.converter.elasticsearch;

import com.obss.hrms.entity.elasticsearch.ApplyAdvertisementElastic;
import com.obss.hrms.response.GetApplyAdvertisementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ApplyAdvertisementElasticConverter {

    private final JobSeekerElasticConverter jobSeekerElasticConverter;

    private final AdvertisementElasticConverter advertisementElasticConverter;

    public List<GetApplyAdvertisementResponse> convert(List<ApplyAdvertisementElastic> fromList){
        if (fromList == null){
            return null;
        }
        return fromList.stream()
                .map(from -> new GetApplyAdvertisementResponse(
                        from.getId(),
                        from.getApplyDate(),
                        from.getApplyAdvertisementStatue(),
                        jobSeekerElasticConverter.convert(from.getJobSeeker()),
                        advertisementElasticConverter.convert(from.getAdvertisement())
                )).toList();
    }
}
