package com.assessment.bank.serverapp.repositories;

import com.assessment.bank.serverapp.models.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByIdentificationNumber(String customerIdentificationNumber);
    Page<Customer> findByFullNameContainingIgnoreCase(String fullName, Pageable pageable);
}
