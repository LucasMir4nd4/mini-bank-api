package com.lucas.bank.mini_bank_api.domain.DTO;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


public record TransactionRequestDTO(

        Long fromAccountId,
        Long toAccountId,
        BigDecimal amount,
        String description

) {}