package com.obss.hrms.controller;


import com.obss.hrms.request.AddPersonalSkillInJobSeekerRequest;
import com.obss.hrms.request.UpdateJobSeekerRequest;
import com.obss.hrms.response.GetJobSeekerResponse;
import com.obss.hrms.response.UpdateJobSeekerResponse;
import com.obss.hrms.service.JobSeekerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/jobSeeker")
@RequiredArgsConstructor
@CrossOrigin("*")
public class JobSeekerController {
    private final JobSeekerService jobSeekerService;

    @GetMapping("/all")
    public ResponseEntity<List<GetJobSeekerResponse>> findAll(){
        return new ResponseEntity<>(jobSeekerService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/byId/{id}")
    public ResponseEntity<GetJobSeekerResponse> findJobSeekerById(@Valid @PathVariable("id") String jobSeekerId){
        return new ResponseEntity<>(jobSeekerService.findJobSeekerById(jobSeekerId), HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity<UpdateJobSeekerResponse> update(@Valid @RequestBody UpdateJobSeekerRequest request){
        return new ResponseEntity<>(jobSeekerService.update(request), HttpStatus.CREATED);
    }
    @PutMapping("/addPersonalSkill")
    public ResponseEntity<GetJobSeekerResponse> addPersonalSkill(@Valid @RequestBody AddPersonalSkillInJobSeekerRequest request){
        return new ResponseEntity<>(jobSeekerService.addPersonalSkill(request), HttpStatus.CREATED);
    }

}
