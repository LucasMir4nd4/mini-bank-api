package com.lucas.bank.mini_bank_api.domain.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public record TransactionResponseDTO(

        @NonNull
        Long id,
        @NonNull
        Long fromAccountId,
        @NonNull
        Long toAccountId,
        @NonNull
        BigDecimal amount,
        @NonNull
        LocalDateTime createAt,
        @NonNull
        String description

) {}