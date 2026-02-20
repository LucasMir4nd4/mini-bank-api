package com.lucas.bank.mini_bank_api.domain.DTO;

import jakarta.validation.constraints.NotNull;


public record AccountRequestDTO(
        @NotNull
        Long customerId
) {}