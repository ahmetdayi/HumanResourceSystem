package com.obss.hrms.response;

import com.obss.hrms.entity.HumanResource;
import com.obss.hrms.entity.HumanResourceEntity;
import com.obss.hrms.entity.JobSeeker;

public record GetBlackListResponse(
        String id,
        String description,
        JobSeeker jobSeeker,
        HumanResourceEntity humanResourceEntity
) {
}
