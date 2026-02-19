package com.lucas.bank.mini_bank_api.domain.DTO;

import java.time.LocalDateTime;
import java.util.List;

public record CustomerResponseDTO(
        Long id,
        String name,
        String cpf,
        String email,
        LocalDateTime createdAt
){}
