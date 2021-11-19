package com.example.demo.repository;

import com.example.demo.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Customer entity.
 */

@Repository
public interface CustomerRepository  extends JpaRepository<Customer, Long> {
}
