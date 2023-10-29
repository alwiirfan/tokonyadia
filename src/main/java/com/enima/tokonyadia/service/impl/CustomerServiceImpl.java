package com.enima.tokonyadia.service.impl;

import com.enima.tokonyadia.entity.Customer;
import com.enima.tokonyadia.repository.CustomerRepository;
import com.enima.tokonyadia.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer create(Customer customer) {
        try {
            return customerRepository.save(customer);
        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "email already used");
        }
    }

    @Override
    public Customer getById(String id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found"));
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }


    @Override
    public Customer update(Customer customer) {
        Customer currentCustomer = getById(customer.getId());

        if (currentCustomer != null) {
            return customerRepository.save(customer);
        }

        return null;
    }

    @Override
    public void deleteById(String id) {
        Customer customer = getById(id);
        customerRepository.delete(customer);
    }
}

