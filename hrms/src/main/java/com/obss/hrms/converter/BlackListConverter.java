package com.obss.hrms.converter;

import com.obss.hrms.entity.BlackList;
import com.obss.hrms.response.CreateBlackListResponse;
import com.obss.hrms.response.GetBlackListResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BlackListConverter {

    public CreateBlackListResponse convert(BlackList from){
        return new CreateBlackListResponse(
                from.getId(),
                from.getDescription(),
                from.getJobSeeker(),
                from.getHumanResourceEntity()
        );
    }

    public List<GetBlackListResponse> convertGet(List<BlackList> fromList){
        if (fromList==null){
            return null;
        }
        return fromList.stream().map(blackList -> new GetBlackListResponse(
                blackList.getId(),
                blackList.getDescription(),
                blackList.getJobSeeker(),
                blackList.getHumanResourceEntity()
        )).collect(Collectors.toList());
    }
    public GetBlackListResponse convertGet(BlackList from){

        return  new GetBlackListResponse(
                from.getId(),
                from.getDescription(),
                from.getJobSeeker(),
                from.getHumanResourceEntity()
        );
    }
}
