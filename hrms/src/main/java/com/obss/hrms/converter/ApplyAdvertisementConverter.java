package com.obss.hrms.converter;

import com.obss.hrms.entity.ApplyAdvertisement;
import com.obss.hrms.response.CreateApplyAdvertisementResponse;
import com.obss.hrms.response.GetApplyAdvertisementResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ApplyAdvertisementConverter {

    public CreateApplyAdvertisementResponse convert(ApplyAdvertisement from) {
        return new CreateApplyAdvertisementResponse(
                from.getId(),
                from.getApplyDate(),
                from.getApplyAdvertisementStatue(),
                from.getJobSeeker(),
                from.getAdvertisement()
        );
    }


    public List<GetApplyAdvertisementResponse> convertGet(List<ApplyAdvertisement> fromList) {
        return Optional.ofNullable(fromList)
                .map(list -> list.stream()
                        .map(applyAdvertisement -> new GetApplyAdvertisementResponse(
                                applyAdvertisement.getId(),
                                applyAdvertisement.getApplyDate(),
                                applyAdvertisement.getApplyAdvertisementStatue(),
                                applyAdvertisement.getJobSeeker(),
                                applyAdvertisement.getAdvertisement()
                        ))
                        .collect(Collectors.toList())
                )
                .orElse(null);
    }

    public GetApplyAdvertisementResponse convertGet(ApplyAdvertisement from) {
        return new GetApplyAdvertisementResponse(
                from.getId(),
                from.getApplyDate(),
                from.getApplyAdvertisementStatue(),
                from.getJobSeeker(),
                from.getAdvertisement()
        );
    }
}
