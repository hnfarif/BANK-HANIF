package com.assessment.bank.serverapp.repositories;

import com.assessment.bank.serverapp.models.Customer;
import com.assessment.bank.serverapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByIdentificationNumber(String customerIdentificationNumber);

}
