package com.obss.hrms.controller;


import com.nimbusds.jose.shaded.gson.Gson;
import com.obss.hrms.converter.JobSeekerConverter;
import com.obss.hrms.entity.HumanResource;
import com.obss.hrms.entity.JobSeeker;
import com.obss.hrms.repository.HumanResourceRepository;
import com.obss.hrms.request.LoginRequest;
import com.obss.hrms.response.GetJobSeekerResponse;
import com.obss.hrms.response.LoginResponse;
import com.obss.hrms.service.AuthService;
import com.obss.hrms.service.JobSeekerAuthUserService;
import com.obss.hrms.service.JobSeekerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.naming.InvalidNameException;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class HomeController {


    private final JobSeekerService jobSeekerService;

    private final AuthService authService;
    private final JobSeekerConverter jobSeekerConverter;

    private final JobSeekerAuthUserService jobSeekerAuthUserService;

    private final HumanResourceRepository humanResourceRepository;

    private String token = null;

    @GetMapping("/human")
    public ResponseEntity<List<HumanResource>> findall() {
        return new ResponseEntity<>(humanResourceRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws InvalidNameException {
        return new ResponseEntity<>(authService.login(loginRequest), HttpStatus.OK);
    }

    @GetMapping("/profile/oauth2")
    public ResponseEntity<GetJobSeekerResponse> profileJobSeeker(OAuth2AuthenticationToken authenticationToken) {
        OAuth2User oauth2User = authenticationToken.getPrincipal();

        String id = oauth2User.getName();
        JobSeeker byId = jobSeekerService.findById(id);
        return ResponseEntity.ok(jobSeekerConverter.convertGet(byId));

    }

    @GetMapping("/access")
    public ResponseEntity<String> hom1() {
        Gson gson = new Gson();
        String tokenJson = gson.toJson(token);
        authService.getUserInfo(token);
        return ResponseEntity.ok(tokenJson);

    }

    @RequestMapping(value = "/oauth/callback")
    public RedirectView handleCallback(@RequestParam(name = "code", required = false) final String code) {
        token = authService.getAccessToken(code);

        return new RedirectView("http://localhost:4200/access");

    }

    @PostMapping("/refresh-token")
    public void jwtRefreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws InvalidNameException, IOException {
        authService.refreshToken(request, response);
    }


}
