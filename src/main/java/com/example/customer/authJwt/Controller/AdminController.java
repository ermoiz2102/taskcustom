package com.example.customer.authJwt.Controller;

import com.example.customer.authJwt.DTO.ReqRes;
import com.example.customer.authJwt.Entity.Customer;
import com.example.customer.authJwt.Repository.customerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class AdminController {
    @Autowired
    private customerRepository customerRepository;

    @GetMapping("/public/customer")
        public ResponseEntity<Object> getAllCustomers(){
            return ResponseEntity.ok(customerRepository.findAll());
        }
    @PostMapping("/admin/saveCustomer")
    public ResponseEntity<Object> signUp(@RequestBody Customer customerRequest){
        Customer customer=Customer.builder()
                .name(customerRequest.getName())

                .build();
        return ResponseEntity.ok(customerRepository.save(customer));
    }
    @GetMapping("/customer/alone")
    public ResponseEntity customerAlone(){
        return ResponseEntity.ok("only customer can access this");
    }
    @GetMapping("/admincustomer/both")
    public ResponseEntity admincustomer(){
        return ResponseEntity.ok("both admin and customer can access this");
    }
    }

