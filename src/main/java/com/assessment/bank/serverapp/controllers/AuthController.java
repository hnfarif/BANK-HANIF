package com.assessment.bank.serverapp.controllers;

import com.assessment.bank.serverapp.models.Customer;
import com.assessment.bank.serverapp.models.dto.requests.LoginRequest;
import com.assessment.bank.serverapp.models.dto.requests.RegistrationRequest;
import com.assessment.bank.serverapp.models.dto.responses.LoginResponse;
import com.assessment.bank.serverapp.services.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Customer> registration (@Valid @RequestBody RegistrationRequest registrationRequest){
        Customer customer = authService.registration(registrationRequest);
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@Valid @RequestBody LoginRequest loginRequest){

        LoginResponse loginResponse = new LoginResponse();

        try {
            loginResponse = authService.login(loginRequest);
        }
        catch (Exception e){
            loginResponse = new LoginResponse();
        }

        return ResponseEntity.ok(loginResponse);
    }
}
