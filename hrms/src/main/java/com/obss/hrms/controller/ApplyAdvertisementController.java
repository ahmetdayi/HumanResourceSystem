package com.obss.hrms.controller;



import com.obss.hrms.request.CreateApplyAdvertisementRequest;

import com.obss.hrms.response.CreateApplyAdvertisementResponse;
import com.obss.hrms.response.GetApplyAdvertisementResponse;

import com.obss.hrms.service.ApplyAdvertisementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/applyAdvertisement")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ApplyAdvertisementController {
    private final ApplyAdvertisementService applyAdvertisementService;


    @PostMapping("/create")
    public ResponseEntity<CreateApplyAdvertisementResponse> create(@Valid @RequestBody CreateApplyAdvertisementRequest request) {
        return new ResponseEntity<>(applyAdvertisementService.create(request), HttpStatus.CREATED);
    }
    @GetMapping("/byAdvertisementId/{id}")
    public ResponseEntity<List<GetApplyAdvertisementResponse>> findByAdvertisementId(@Valid @PathVariable("id") String advertisementId){
        return new ResponseEntity<>(applyAdvertisementService.findByAdvertisementId(advertisementId),HttpStatus.OK);
    }
    @GetMapping("/byJobSeekerId/{id}")
    public ResponseEntity<List<GetApplyAdvertisementResponse>> findByJobSeekerId(@Valid @PathVariable("id") String jobSeekerId){
        return new ResponseEntity<>(applyAdvertisementService.findByJobSeekerId(jobSeekerId),HttpStatus.OK);
    }
    @GetMapping("/byApplyAdvertisementId/{id}")
    public ResponseEntity<GetApplyAdvertisementResponse> findByApplyAdvertisementId(@Valid @PathVariable("id") String applyAdvertisementId){
        return new ResponseEntity<>(applyAdvertisementService.findByApplyAdvertisementId(applyAdvertisementId),HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<GetApplyAdvertisementResponse>> findAll(){
        return new ResponseEntity<>(applyAdvertisementService.findAll(),HttpStatus.OK);
    }
    @PutMapping("/changeStatue/{statue}/{applyAdvertisementId}")
    public ResponseEntity<Void> updateApplyAdvertisementStatue(@Valid @PathVariable("statue") String statue,@Valid @PathVariable("applyAdvertisementId") String applyAdvertisementId){
        applyAdvertisementService.updateApplyAdvertisementStatue(statue, applyAdvertisementId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/filter/{statue}")
    public ResponseEntity<List<GetApplyAdvertisementResponse>> filter(@Valid @PathVariable("statue") String statue){
        return new ResponseEntity<>(applyAdvertisementService.filter(statue),HttpStatus.OK);
    }
    @GetMapping("/sortedMatch")
    public ResponseEntity<List<GetApplyAdvertisementResponse>> findAllApplyAdvertisementsSortedByMatchCount(){
        return new ResponseEntity<>(applyAdvertisementService.findAllApplyAdvertisementsSortedByMatchCount(),HttpStatus.OK);
    }

}
