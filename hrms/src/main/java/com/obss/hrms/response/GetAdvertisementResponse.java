package com.obss.hrms.response;

import com.obss.hrms.entity.AdvertisementStatue;
import com.obss.hrms.entity.HumanResourceEntity;
import com.obss.hrms.entity.PersonalSkill;

import java.time.LocalDate;
import java.util.List;

public record GetAdvertisementResponse(
        String id,
        String advCode,
        String title,
        String jobDescription,
        LocalDate activationTime,
        LocalDate offDate,
        AdvertisementStatue advertisementStatue,
        HumanResourceEntity humanResourceEntity,
        List<PersonalSkill> personalSkills
) {
}
