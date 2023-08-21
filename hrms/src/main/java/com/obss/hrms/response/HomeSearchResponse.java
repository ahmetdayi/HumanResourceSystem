package com.obss.hrms.response;

import java.util.List;

public record HomeSearchResponse(
        List<GetJobSeekerResponse> jobSeeker,
        List<com.obss.hrms.entity.elasticsearch.AdvertisementElastic> advertisement
) {
}
