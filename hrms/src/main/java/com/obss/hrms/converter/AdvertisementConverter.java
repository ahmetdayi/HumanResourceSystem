package com.obss.hrms.converter;

import com.obss.hrms.entity.Advertisement;
import com.obss.hrms.response.CreateAdvertisementResponse;
import com.obss.hrms.response.GetAdvertisementResponse;
import com.obss.hrms.response.UpdateAdvertisementResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdvertisementConverter {

    public CreateAdvertisementResponse convert(Advertisement from){
        return new CreateAdvertisementResponse(
                from.getId(),
                from.getAdvCode(),
                from.getTitle(),
                from.getJobDescription(),
                from.getActivationTime(),
                from.getOffDate(),
                from.getAdvertisementStatue()
        );
    }
    public UpdateAdvertisementResponse convertUpdate(Advertisement from){
        return new UpdateAdvertisementResponse(
                from.getId(),
                from.getAdvCode(),
                from.getTitle(),
                from.getAdvertisementStatue(),
                from.getActivationTime(),
                from.getOffDate(),
                from.getJobDescription(),
                from.getPersonalSkills(),
                from.getHumanResourceEntity()
        );
    }
    public List<GetAdvertisementResponse> convertGet(List<Advertisement> fromList){
        if (fromList == null){
            return null;
        }
        return fromList.stream()
                .map(advertisement -> new GetAdvertisementResponse(
                        advertisement.getId(),
                        advertisement.getAdvCode(),
                        advertisement.getTitle(),
                        advertisement.getJobDescription(),
                        advertisement.getActivationTime(),
                        advertisement.getOffDate(),
                        advertisement.getAdvertisementStatue(),
                        advertisement.getHumanResourceEntity(),
                        advertisement.getPersonalSkills()
                )).toList();
    }
    public GetAdvertisementResponse convertGet(Advertisement advertisement){

        return  new GetAdvertisementResponse(
                        advertisement.getId(),
                        advertisement.getAdvCode(),
                        advertisement.getTitle(),
                        advertisement.getJobDescription(),
                        advertisement.getActivationTime(),
                        advertisement.getOffDate(),
                        advertisement.getAdvertisementStatue(),
                        advertisement.getHumanResourceEntity(),
                        advertisement.getPersonalSkills()
                );
    }
}
