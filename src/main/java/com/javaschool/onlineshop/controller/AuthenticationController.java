package com.javaschool.onlineshop.controller;

import com.javaschool.onlineshop.exception.LoginRequestException;
import com.javaschool.onlineshop.security.authData.AuthenticationRequest;
import com.javaschool.onlineshop.security.authData.JwtResponse;
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

/**
 * This class is responsible for handling the authentication request.
 */
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

    /**
     * This method is responsible for handling request to user authentication
     *
     * @param request it contains information required for authentication
     * @return contains email, password, token of authenticated user
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            return ResponseEntity.ok(customerService.login(request.getUsername()));
        } catch (AuthenticationException e) {
            throw new LoginRequestException("Incorrect password or email", HttpStatus.FORBIDDEN);
        }
    }
}
