package com.javaschool.onlineshop.controller;

import com.javaschool.onlineshop.exception.LoginRequestException;
import com.javaschool.onlineshop.model.dto.AuthenticationRequestDTO;
import com.javaschool.onlineshop.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/authentication")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final CustomerService customerService;

    public AuthenticationController(AuthenticationManager authenticationManager, CustomerService customerService) {
        this.authenticationManager = authenticationManager;
        this.customerService = customerService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthenticationRequestDTO request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            return ResponseEntity.ok(customerService.login(request.getUsername()));
        } catch (AuthenticationException e) {
            throw new LoginRequestException("Incorrect password or email", HttpStatus.FORBIDDEN);
        }
    }
}
