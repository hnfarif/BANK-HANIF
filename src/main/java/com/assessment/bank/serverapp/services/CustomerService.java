package com.assessment.bank.serverapp.services;

import com.assessment.bank.serverapp.models.Customer;
import com.assessment.bank.serverapp.models.User;
import com.assessment.bank.serverapp.models.dto.requests.CustomerCreateRequest;
import com.assessment.bank.serverapp.models.dto.requests.CustomerUpdateRequest;
import com.assessment.bank.serverapp.models.dto.responses.PageResponse;
import com.assessment.bank.serverapp.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.util.function.Consumer;

@Service
@AllArgsConstructor
@Transactional
public class CustomerService {

    private CustomerRepository customerRepository;
    private static final Logger logger = LogManager.getLogger(CustomerService.class);
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    public PageResponse<Customer> getAll(int pageNo, int pageSize) {
        try {
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            Page<Customer> customers = customerRepository.findAll(pageable);
            logger.info("Customer Service - getAll : Success!");
            return new PageResponse<>(
                    customers.getContent(),
                    customers.getNumber() + 1,
                    customers.getTotalPages(),
                    customers.getTotalElements(),
                    customers.getNumberOfElements(),
                    customers.getSize()
            );
        }catch (Exception e){
            logger.error("Customer Service - getAll : {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public PageResponse<Customer> searchByFullName(String name, int pageNo, int pageSize) {
        try {
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            Page<Customer> customers = customerRepository.findByFullNameContainingIgnoreCase(name, pageable);
            logger.info("Customer Service - searchByFullName : Success!");
            return new PageResponse<>(
                    customers.getContent(),
                    customers.getNumber() + 1,
                    customers.getTotalPages(),
                    customers.getTotalElements(),
                    customers.getNumberOfElements(),
                    customers.getSize()
            );
        }catch (Exception e){
            logger.info("Customer Service - searchByFullName : {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public Customer create(CustomerCreateRequest customerCreateRequest) {

        try {
            Customer customer = modelMapper.map(customerCreateRequest, Customer.class);
            User user = modelMapper.map(customerCreateRequest, User.class);

            user.setPassword(passwordEncoder.encode(customerCreateRequest.getPassword()));

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            if (customerCreateRequest.getDateOfBirth() != null && !customerCreateRequest.getDateOfBirth().isBlank())
                customer.setDateOfBirth(formatter.parse(customerCreateRequest.getDateOfBirth()));

            updateFieldIfNotNullOrBlank(customer::setPhoneNumber, customerCreateRequest.getPhoneNumber());
            customer.setUser(user);
            user.setCustomer(customer);
            customerRepository.save(customer);

            logger.info("Customer Service - Create : Success!");
            return customer;
        }catch (Exception e){
            logger.error("Customer Service - Create : {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public Customer getByIdentificationNumber(String identificationNumber) {
        Customer customer = customerRepository.findByIdentificationNumber(identificationNumber)
                .orElseThrow(() -> {
                    logger.error("Customer Service - getByIdentificationNumber : Customer Not Found!");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Not Found!");
                });
        logger.info("Customer Service - getByIdentificationNumber : Success!");
        return customer;
    }

    public Customer update(String identificationNumber, CustomerUpdateRequest customerRequest) {

        Customer customer = getByIdentificationNumber(identificationNumber);
        User user = customer.getUser();
        try {
            updateFieldIfNotNullOrBlank(customer::setFullName, customerRequest.getFullName());
            updateFieldIfNotNullOrBlank(customer::setAddress, customerRequest.getAddress());
            updateFieldIfNotNullOrBlank(customer::setIdentificationNumber, customerRequest.getIdentificationNumber());
            updateFieldIfNotNullOrBlank(customer::setPlaceOfBirth, customerRequest.getPlaceOfBirth());

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            if (customerRequest.getDateOfBirth() != null && !customerRequest.getDateOfBirth().isBlank())
                customer.setDateOfBirth(formatter.parse(customerRequest.getDateOfBirth()));

            updateFieldIfNotNullOrBlank(customer::setPhoneNumber, customerRequest.getPhoneNumber());
            updateFieldIfNotNullOrBlank(user::setUsername, customerRequest.getUsername());

            if (customerRequest.getPassword() != null && !customerRequest.getPassword().isBlank())
                user.setPassword(passwordEncoder.encode(customerRequest.getPassword()));

            customerRepository.save(customer);
            logger.info("Customer Service - update : Success!");
            return customer;
        } catch (Exception e){
            logger.error("Customer Service - update : {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public Customer delete(String identificationNumber) {

        Customer customer = getByIdentificationNumber(identificationNumber);
        try {
            customerRepository.delete(customer);
            logger.info("Customer Service - delete : Success!");
            return customer;
        }catch (Exception e){
            logger.error("Customer Service - delete : {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void updateFieldIfNotNullOrBlank(Consumer<String> setter, String value) {
        if (value != null && !value.isBlank()) {
            setter.accept(value);
        }
    }

}
