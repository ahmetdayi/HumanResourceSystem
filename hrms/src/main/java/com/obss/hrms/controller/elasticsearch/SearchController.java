package com.obss.hrms.controller.elasticsearch;

import com.obss.hrms.entity.elasticsearch.AdvertisementElastic;
import com.obss.hrms.entity.elasticsearch.ApplyAdvertisementElastic;
import com.obss.hrms.response.*;
import com.obss.hrms.service.elasticsearch.AdvertisementElasticService;
import com.obss.hrms.service.elasticsearch.ApplyAdvertisementElasticService;
import com.obss.hrms.service.elasticsearch.JobSeekerElasticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class SearchController {

    private final JobSeekerElasticService jobSeekerElasticService;

    private final AdvertisementElasticService advertisementElasticService;

    private final ApplyAdvertisementElasticService applyAdvertisementElasticService;

    @GetMapping("/homePageSearch/{query}")
    public ResponseEntity<HomeSearchResponse> homeSearch(@PathVariable("query") String query){
        List<AdvertisementElastic> advertisement = advertisementElasticService.searchAdvertisement(query);
        List<GetJobSeekerResponse> jobSeeker = jobSeekerElasticService.search(query);
        return new ResponseEntity<>(new HomeSearchResponse(jobSeeker,advertisement), HttpStatus.OK);
    }

    @GetMapping("/applyPageSearch/{query}")
    public ResponseEntity<ApplySearchResponse> applySearch(@PathVariable("query") String query) {
        List<ApplyAdvertisementElastic> applyAdvertisement = applyAdvertisementElasticService
                .searchApplyAdvertisement(query);
        return new ResponseEntity<>(new ApplySearchResponse(applyAdvertisement),HttpStatus.OK);
    }
}
