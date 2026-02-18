package com.lucas.bank.mini_bank_api.controller;

import com.lucas.bank.mini_bank_api.domain.DTO.CustomerRequestDTO;
import com.lucas.bank.mini_bank_api.domain.DTO.CustomerResponseDTO;
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
    public CustomerResponseDTO create(
            @RequestBody @Valid CustomerRequestDTO request
    ) {
        return service.create(request);
    }

    @GetMapping
    public List<CustomerResponseDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public CustomerResponseDTO findById(@PathVariable Long id) {
        return service.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

}
