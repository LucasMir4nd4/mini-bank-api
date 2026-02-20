package com.lucas.bank.mini_bank_api.controller;

import com.lucas.bank.mini_bank_api.domain.DTO.TransactionRequestDTO;
import com.lucas.bank.mini_bank_api.domain.DTO.TransactionResponseDTO;
import com.lucas.bank.mini_bank_api.domain.entity.Transaction;
import com.lucas.bank.mini_bank_api.service.TransactionService;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponseDTO> transfer(@RequestBody TransactionRequestDTO request) {
        return ResponseEntity.ok(transactionService.tranfer(request));
    }

    @GetMapping
    public List<Transaction> findAll() {
        return ResponseEntity.ok(transactionService.findAll()).getBody();
    }

}
