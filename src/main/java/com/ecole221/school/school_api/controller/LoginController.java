package com.ecole221.school.school_api.controller;


import com.ecole221.school.school_api.entity.LoginRequest;
import com.ecole221.school.school_api.entity.LoginResponse;
import com.ecole221.school.school_api.service.LoginService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {


    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest loginRequest) throws Exception {
        log.info("Executing login");

        ResponseEntity<LoginResponse> response = null;
        response = loginService.login(loginRequest);

        return response;
    }
}