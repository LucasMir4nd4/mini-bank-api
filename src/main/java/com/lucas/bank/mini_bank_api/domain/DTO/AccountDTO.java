package com.lucas.bank.mini_bank_api.domain.DTO;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter

public class AccountDTO {

    private String accountNumber;
    private Double balance;
    private Boolean active;
    private LocalDateTime createdAt;


}
