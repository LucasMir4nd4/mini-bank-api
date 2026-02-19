package com.lucas.bank.mini_bank_api.domain.DTO;

import com.lucas.bank.mini_bank_api.domain.entity.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;


public record AccountRequestDTO(

        @NotNull
        Long customerId
) {}