package com.obss.hrms.converter;


import com.obss.hrms.entity.AdvertisementStatue;
import com.obss.hrms.exception.AdvertisementStatueIsWrongException;
import com.obss.hrms.exception.Constant;

import org.springframework.stereotype.Component;



@Component
public class AdvertisementStatueConverter {

    public AdvertisementStatue convert(String from){

        AdvertisementStatue advertisementStatue = AdvertisementStatue.valueOf(from.toUpperCase());
        if (advertisementStatue == null) {
            throw new AdvertisementStatueIsWrongException(Constant.ADVERTISEMENT_STATUE_IS_WRONG);
        }
        return advertisementStatue;
    }


}
