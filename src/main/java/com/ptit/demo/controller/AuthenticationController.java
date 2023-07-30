package com.ptit.demo.controller;

import com.ptit.demo.dto.JwtResponse;
import com.ptit.demo.dto.LoginRequest;
import com.ptit.demo.dto.RefreshTokenRequest;
import com.ptit.demo.dto.RegisterRequest;
import com.ptit.demo.service.AuthenticationService;
import com.ptit.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationServiceService;
    @PostMapping("/register")
    public JwtResponse registerUser(@RequestBody RegisterRequest request) {
        return authenticationServiceService.register(request);
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest request) {
        return authenticationServiceService.login(request);
    }

    @PostMapping("/refresh-token")
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest request) {
        return authenticationServiceService.refreshToken(request);
    }
}

