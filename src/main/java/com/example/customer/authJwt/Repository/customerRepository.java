package com.example.customer.authJwt.Repository;

import com.example.customer.authJwt.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface customerRepository extends JpaRepository<Customer,Integer> {

    @Override
    Optional<Customer> findById(Integer integer);
}
