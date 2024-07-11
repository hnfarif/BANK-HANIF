package com.assessment.bank.serverapp.services;

import com.assessment.bank.serverapp.models.Customer;
import com.assessment.bank.serverapp.models.dto.requests.CustomerUpdateRequest;
import com.assessment.bank.serverapp.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class CustomerService {

    private CustomerRepository customerRepository;

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer getByidentificationNumber(String identificationNumber) {
        return customerRepository.findByIdentificationNumber(identificationNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nasabah Tidak Ditemukan!"));
    }

    public Customer getById(Integer id){
        return customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Tidak Ditemukan!"));
    }

    public Customer update(String identificationNumber, CustomerUpdateRequest customerUpdateRequest) throws ParseException {

        try {
            Customer customer = getByidentificationNumber(identificationNumber);
            customer.setIdentificationNumber(identificationNumber);
            customer.setFullName(customerUpdateRequest.getFullName());
            customer.setAddress(customerUpdateRequest.getAddress());
            customer.setPlaceOfBirth(customerUpdateRequest.getPlaceOfBirth());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            customer.setDateOfBirth(formatter.parse(customerUpdateRequest.getDateOfBirth()));
            customer.setPhoneNumber(customerUpdateRequest.getPhoneNumber());

            return customerRepository.save(customer);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public Customer delete(Integer id) {
        Customer customer = getById(id);
        customerRepository.delete(customer);
        return customer;
    }

}
