package com.lucas.bank.mini_bank_api.controller;

import com.lucas.bank.mini_bank_api.domain.DTO.AccountRequestDTO;
import com.lucas.bank.mini_bank_api.domain.DTO.AccountResponseDTO;
import com.lucas.bank.mini_bank_api.domain.DTO.CustomerRequestDTO;
import com.lucas.bank.mini_bank_api.domain.DTO.CustomerResponseDTO;
import com.lucas.bank.mini_bank_api.domain.entity.Account;
import com.lucas.bank.mini_bank_api.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    @Autowired
    private final AccountService service;

    @PostMapping
    public AccountResponseDTO create(
            @RequestBody @Valid AccountRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request)).getBody() ;
    }


    @GetMapping
    public List<AccountResponseDTO> findAll() {
        return service.findAll();
    }

}
