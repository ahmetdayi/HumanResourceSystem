package com.obss.hrms.controller;


import com.obss.hrms.entity.PersonalSkill;
import com.obss.hrms.request.CreatePersonalSkillRequest;
import com.obss.hrms.response.CreatePersonalSkillResponse;
import com.obss.hrms.service.PersonalSkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/personalSkill")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PersonalSkillController {
    private final PersonalSkillService personalSkillService;

    @PostMapping("/create")
    public ResponseEntity<CreatePersonalSkillResponse> create(@Valid @RequestBody CreatePersonalSkillRequest request){
        return new ResponseEntity<>(personalSkillService.create(request), HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@Valid @PathVariable("id") String personalSkillId){
        personalSkillService.delete(personalSkillId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<PersonalSkill>> findAll(){
        return new ResponseEntity<>(personalSkillService.findAllPersonalSkill(),HttpStatus.OK);
    }
}
