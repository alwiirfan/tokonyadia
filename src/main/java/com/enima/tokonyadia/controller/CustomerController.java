package com.enima.tokonyadia.controller;

import com.enima.tokonyadia.entity.Customer;
import com.enima.tokonyadia.model.response.CommonResponse;
import com.enima.tokonyadia.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> createNewCustomer(@RequestBody Customer customer) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<Customer>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully create new customer")
                        .data(customerService.create(customer))
                        .build());
    }

    @GetMapping
    public ResponseEntity<?> getAllCustomer() {
        List<Customer> customers = customerService.getAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get all customer")
                        .data(customers)
                        .build());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<Customer>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get customer by id")
                        .data(customerService.getById(id))
                        .build());
    }

    @PutMapping
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<Customer>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully update customer")
                        .data(customerService.update(customer))
                        .build());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id) {
        customerService.deleteById(id);
        Customer customer = new Customer();
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully delete customer")
                        .data(String.valueOf(customer))
                        .build());
    }
}
