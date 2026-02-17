package com.lucas.bank.mini_bank_api.controller;

import com.lucas.bank.mini_bank_api.domain.DTO.CustomerDTO;
import com.lucas.bank.mini_bank_api.domain.entity.Customer;
import com.lucas.bank.mini_bank_api.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    @Autowired
    private final CustomerService service;

    @PostMapping
    public Customer create(@RequestBody @Valid CustomerDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<Customer> findAll() {
        return service.findAll();
    }

}
