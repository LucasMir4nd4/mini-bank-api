package com.lucas.bank.mini_bank_api.domain.DTO;

import com.lucas.bank.mini_bank_api.domain.entity.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;


public record AccountResponseDTO(

        @NotBlank
        Long id,
        @NotBlank
        String accountNumber,
        @NotBlank
        Double balance,
        @NotBlank
        Boolean active,
        @NotBlank
        LocalDateTime createdAt,
        @NotBlank
        CustomerIdDTO customerIdDTO


) {
}