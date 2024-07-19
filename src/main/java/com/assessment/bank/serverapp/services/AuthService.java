package com.assessment.bank.serverapp.services;

import com.assessment.bank.serverapp.models.Customer;
import com.assessment.bank.serverapp.models.User;
import com.assessment.bank.serverapp.models.dto.requests.CustomerCreateRequest;
import com.assessment.bank.serverapp.models.dto.requests.LoginRequest;
import com.assessment.bank.serverapp.models.dto.responses.LoginResponse;
import com.assessment.bank.serverapp.repositories.CustomerRepository;
import com.assessment.bank.serverapp.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

    private CustomerRepository customerRepository;
    private UserRepository userRepository;
    private static final Logger logger = LogManager.getLogger(AuthService.class);
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    public Customer registration(CustomerCreateRequest customerCreateRequest) {

        try {
            Customer customer = modelMapper.map(customerCreateRequest, Customer.class);
            User user = modelMapper.map(customerCreateRequest, User.class);

            user.setPassword(passwordEncoder.encode(customerCreateRequest.getPassword()));

            customer.setUser(user);
            user.setCustomer(customer);

            Customer savedCustomer = customerRepository.save(customer);
            logger.info("Auth Service - Registration : Success!");
            return savedCustomer;
        }catch (Exception e) {
            logger.error("Auth Service - Registration : {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    public LoginResponse login(LoginRequest loginRequest) {

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userRepository.findByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> {
                        logger.error("Auth Service - login : Customer Not Found!");
                        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Not Found!");
                    });
            logger.info("Auth Service - login : Success!");
            return new LoginResponse(user.getUsername(), user.getCustomer().getFullName());

        }catch (Exception e){
            logger.error("Auth Service - Login : {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }
}
