package com.lucas.bank.mini_bank_api.service;

import com.lucas.bank.mini_bank_api.domain.DTO.CustomerDTO;
import com.lucas.bank.mini_bank_api.domain.entity.Customer;
import com.lucas.bank.mini_bank_api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    @Autowired
    private final CustomerRepository customerRepository;

    public Customer create(CustomerDTO customerDTO) {

        if (customerRepository.existsByCpf(customerDTO.getCpf())) {
            throw new RuntimeException("CPF already registered");
        }
        if (customerRepository.existsByEmail(customerDTO.getEmail())) {
            throw new RuntimeException("Email already registered");
        }


        Customer customer = Customer.builder()
                .name(customerDTO.getName())
                .email(customerDTO.getEmail())
                .cpf(customerDTO.getCpf())
                .password(customerDTO.getPassword())
                .build(
        );

        return customerRepository.save(customer);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }


}
