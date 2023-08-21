package com.obss.hrms.response;

import com.obss.hrms.entity.AdvertisementStatue;
import com.obss.hrms.entity.HumanResourceEntity;
import com.obss.hrms.entity.PersonalSkill;

import java.time.LocalDate;
import java.util.List;

public record UpdateAdvertisementResponse(
        String id,
        String advCode,
        String title,
        AdvertisementStatue advertisementStatue,
        LocalDate activationTime,
        LocalDate offDate,
        String jobDescription,
        List<PersonalSkill> personalSkills,
        HumanResourceEntity humanResourceEntity

) {
}
