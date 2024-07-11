package com.assessment.bank.serverapp.controllers;

import com.assessment.bank.serverapp.models.Customer;
import com.assessment.bank.serverapp.models.dto.requests.CustomerUpdateRequest;
import com.assessment.bank.serverapp.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customerList = customerService.getAll();
        return ResponseEntity.ok(customerList);
    }

    @GetMapping("/{identificationNumber}")
    public ResponseEntity<Customer> getCustomerByIdentificationNumber(@PathVariable String identificationNumber) {
        Customer customer = customerService.getByidentificationNumber(identificationNumber);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{identificationNumber}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String identificationNumber, @RequestBody CustomerUpdateRequest customerUpdateRequest) throws ParseException {
        Customer customer = customerService.update(identificationNumber, customerUpdateRequest);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Integer id) {
        Customer customer = customerService.delete(id);
        return ResponseEntity.ok(customer);
    }
}
