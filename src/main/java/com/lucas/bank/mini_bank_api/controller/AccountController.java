package com.lucas.bank.mini_bank_api.controller;

import com.lucas.bank.mini_bank_api.domain.DTO.AccountDTO;
import com.lucas.bank.mini_bank_api.domain.DTO.CustomerDTO;
import com.lucas.bank.mini_bank_api.domain.entity.Account;
import com.lucas.bank.mini_bank_api.domain.entity.Customer;
import com.lucas.bank.mini_bank_api.service.AccountService;
import com.lucas.bank.mini_bank_api.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    @Autowired
    private final AccountService service;

    @PostMapping
    public Account create(@RequestBody @Valid AccountDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<Account> findAll() {
        return service.findAll();
    }

}
