package com.obss.hrms.controller;


import com.obss.hrms.request.CreateBlackListRequest;

import com.obss.hrms.response.CreateBlackListResponse;
import com.obss.hrms.response.GetBlackListResponse;
import com.obss.hrms.service.BlackListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.InvalidNameException;
import java.util.List;

@RestController
@RequestMapping("/blackList")
@RequiredArgsConstructor
@CrossOrigin("*")
public class BlackListController {
    private final BlackListService blackListService;

    @PostMapping("/create")
    public ResponseEntity<CreateBlackListResponse> create(@Valid @RequestBody CreateBlackListRequest request) throws InvalidNameException {
        return new ResponseEntity<>(blackListService.create(request), HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<GetBlackListResponse>> findAll(){
        return new ResponseEntity<>(blackListService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/byJobSeekerId/{id}")
    public ResponseEntity<GetBlackListResponse> findByJobSeekerId(@PathVariable("id") String jobSeekerId){
        return new ResponseEntity<>(blackListService.findBlackListByJobSeekerId(jobSeekerId), HttpStatus.OK);
    }

    @GetMapping("/byBlackListId/{id}")
    public ResponseEntity<GetBlackListResponse> findByBlackListId(@PathVariable("id") String blackListId){
        return new ResponseEntity<>(blackListService.findByBlackListId(blackListId), HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@Valid @PathVariable("id") String blackListId ){
        blackListService.delete(blackListId);
        return new ResponseEntity<>( HttpStatus.OK);
    }


}
