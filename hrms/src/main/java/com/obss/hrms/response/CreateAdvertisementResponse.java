package com.obss.hrms.response;


import com.obss.hrms.entity.AdvertisementStatue;

import java.time.LocalDate;

public record CreateAdvertisementResponse(
        String id,
        String advCode,
        String title,
        String jobDescription,
        LocalDate activationTime,
        LocalDate offDate,
        AdvertisementStatue advertisementStatue
) {
}
