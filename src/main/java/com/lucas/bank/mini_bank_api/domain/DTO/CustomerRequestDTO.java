package com.lucas.bank.mini_bank_api.domain.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record CustomerRequestDTO(

        @NotBlank
        String name,

        @NotBlank
        @Size(min = 11, max = 11)
        String cpf,

        @Email
        @NotBlank
        String email,

        @NotBlank
        @Size(min = 6)
        String password

) {}