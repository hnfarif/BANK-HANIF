package com.assessment.bank.serverapp.services;

import com.assessment.bank.serverapp.models.Customer;
import com.assessment.bank.serverapp.models.User;
import com.assessment.bank.serverapp.models.dto.requests.LoginRequest;
import com.assessment.bank.serverapp.models.dto.requests.RegistrationRequest;
import com.assessment.bank.serverapp.models.dto.responses.LoginResponse;
import com.assessment.bank.serverapp.repositories.CustomerRepository;
import com.assessment.bank.serverapp.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

    private CustomerRepository customerRepository;
    private AuthenticationManager authManager;
    private UserRepository userRepository;
    private AppUserDetailService appUserDetailService;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest loginRequest) {

        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword());

            // set principle
            Authentication auth = authManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(auth);

            User user = userRepository.findByUsername(loginRequest.getUsername()).get();

            return new LoginResponse(
                    user.getUsername(),
                    user.getCustomer().getFullName());

        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    public Customer registration(RegistrationRequest registrationRequest) {

        try {
            Customer customer = modelMapper.map(registrationRequest, Customer.class);
            User user = modelMapper.map(registrationRequest, User.class);

            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

            customer.setUser(user);
            user.setCustomer(customer);

            return customerRepository.save(customer);

        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
