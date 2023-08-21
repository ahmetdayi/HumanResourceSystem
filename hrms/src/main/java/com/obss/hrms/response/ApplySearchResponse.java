package com.obss.hrms.response;

import com.obss.hrms.entity.elasticsearch.ApplyAdvertisementElastic;

import java.util.List;

public record ApplySearchResponse(
        List<ApplyAdvertisementElastic> applyAdvertisement
) {
}
