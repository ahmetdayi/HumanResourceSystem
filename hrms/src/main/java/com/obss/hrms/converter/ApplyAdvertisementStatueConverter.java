package com.obss.hrms.converter;

import com.obss.hrms.entity.ApplyAdvertisementStatue;
import com.obss.hrms.exception.ApplyAdvertisementStatueIsWrongException;
import com.obss.hrms.exception.Constant;
import org.springframework.stereotype.Component;

@Component
public class ApplyAdvertisementStatueConverter {

    public ApplyAdvertisementStatue convert(String from) {
        try {
            return ApplyAdvertisementStatue.valueOf(from.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ApplyAdvertisementStatueIsWrongException(Constant.APPLY_ADVERTISEMENT_STATUE_IS_WRONG);
        }
    }

}
