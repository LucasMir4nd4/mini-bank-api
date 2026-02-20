package com.lucas.bank.mini_bank_api.domain.DTO;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public record AccountResponseDTO(

        @NotBlank
        Long id,
        @NotBlank
        String accountNumber,
        @NotBlank
        BigDecimal balance,
        @NotBlank
        Boolean active,
        @NotBlank
        LocalDateTime createdAt,
        @NotBlank
        CustomerIdDTO customerIdDTO


) {
}