package com.assessment.bank.serverapp.controllers;

import com.assessment.bank.serverapp.models.Customer;
import com.assessment.bank.serverapp.models.dto.requests.CustomerCreateRequest;
import com.assessment.bank.serverapp.models.dto.requests.CustomerUpdateRequest;
import com.assessment.bank.serverapp.models.dto.responses.PageResponse;
import com.assessment.bank.serverapp.services.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<PageResponse<Customer>> getAllCustomers(@RequestParam(defaultValue = "1") int pageNo,
                                                          @RequestParam(defaultValue = "10") int pageSize) {
        PageResponse<Customer> customers = customerService.getAll(pageNo - 1, pageSize);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<Customer>> searchCustomer(@RequestParam String name, @RequestParam(defaultValue = "1") int pageNo,
                                                         @RequestParam(defaultValue = "10") int pageSize) {
        PageResponse<Customer> customers = customerService.searchByFullName(name, pageNo - 1 , pageSize);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createCustomer(@Valid @RequestBody CustomerCreateRequest customerCreateRequest){
        Customer customer = customerService.create(customerCreateRequest);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.CREATED.value());
        responseBody.put("message", "Customer created successfully!");
        responseBody.put("data", customer);
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @GetMapping("/{identificationNumber}")
    public ResponseEntity<Customer> getCustomerByIdentificationNumber(@PathVariable String identificationNumber) {
        Customer customer = customerService.getByIdentificationNumber(identificationNumber);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PutMapping("/{identificationNumber}")
    public ResponseEntity<Map<String, Object>> updateCustomer(@PathVariable String identificationNumber, @Valid @RequestBody CustomerUpdateRequest customerRequest)  {
        Customer customer = customerService.update(identificationNumber, customerRequest);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.OK.value());
        responseBody.put("message", "Customer Updated successfully!");
        responseBody.put("data", customer);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{identificationNumber}")
    public ResponseEntity<Map<String, Object>> deleteCustomer(@PathVariable String identificationNumber) {
        Customer customer = customerService.delete(identificationNumber);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.OK.value());
        responseBody.put("message", String.format("Customer %s has been deleted!", customer.getFullName()));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
