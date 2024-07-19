package com.assessment.bank.serverapp.controllers;

import com.assessment.bank.serverapp.models.Customer;
import com.assessment.bank.serverapp.models.dto.requests.CustomerCreateRequest;
import com.assessment.bank.serverapp.models.dto.requests.LoginRequest;
import com.assessment.bank.serverapp.models.dto.responses.LoginResponse;
import com.assessment.bank.serverapp.services.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registration (@Valid @RequestBody CustomerCreateRequest customerCreateRequest){

        Map<String, Object> responseBody = new HashMap<>();
        Customer customer = authService.registration(customerCreateRequest);

        responseBody.put("status", HttpStatus.CREATED.value());
        responseBody.put("message", "Customer register successfully!");
        responseBody.put("data", customer);

        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login (@Valid @RequestBody LoginRequest loginRequest){

        LoginResponse loginResponse = authService.login(loginRequest);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.CREATED.value());
        responseBody.put("message", "Customer Login successfully!");
        responseBody.put("data", loginResponse);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
