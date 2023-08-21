package com.obss.hrms.response;

import com.obss.hrms.entity.Advertisement;
import com.obss.hrms.entity.ApplyAdvertisementStatue;
import com.obss.hrms.entity.JobSeeker;

import java.time.LocalDate;

public record GetApplyAdvertisementResponse(
        String id,
        LocalDate applyDate,
        ApplyAdvertisementStatue applyAdvertisementStatue,
        JobSeeker jobSeeker,
        Advertisement advertisement
) {
}
