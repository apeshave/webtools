package com.example.project.repository;

import com.example.project.dto.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findCustomerByName(String name);
    Customer findCustomerByEmail(String email);
}

