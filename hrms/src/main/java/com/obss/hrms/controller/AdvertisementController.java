package com.obss.hrms.controller;

import com.obss.hrms.request.CreateAdvertisementRequest;
import com.obss.hrms.request.UpdateAdvertisementRequest;
import com.obss.hrms.response.CreateAdvertisementResponse;
import com.obss.hrms.response.GetAdvertisementResponse;
import com.obss.hrms.response.UpdateAdvertisementResponse;
import com.obss.hrms.service.AdvertisementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.InvalidNameException;
import java.util.List;

@RestController
@RequestMapping("/advertisement")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AdvertisementController {
    private final AdvertisementService advertisementService;


    @GetMapping("/all")
    public ResponseEntity<List<GetAdvertisementResponse>> findAll(){
        return new ResponseEntity<>(advertisementService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<GetAdvertisementResponse> findById(@PathVariable("id")  String id){
        return new ResponseEntity<>(advertisementService.findAdvertisementId(id),HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CreateAdvertisementResponse> create(@Valid @RequestBody CreateAdvertisementRequest request) throws InvalidNameException {
        return new ResponseEntity<>(advertisementService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/changeStatue/{statue}/{id}")
    public ResponseEntity<Void> changeAdvertisementStatue(@Valid @PathVariable("statue") boolean statue,@Valid @PathVariable("id") String advertisementId) {
        advertisementService.changeAdvertisementStatue(statue, advertisementId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<UpdateAdvertisementResponse> update(@Valid @RequestBody UpdateAdvertisementRequest request) {
        return new ResponseEntity<>(advertisementService.update(request), HttpStatus.CREATED);
    }


}
