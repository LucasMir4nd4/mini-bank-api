package com.lucas.bank.mini_bank_api.service;

import com.lucas.bank.mini_bank_api.domain.DTO.AccountDTO;
import com.lucas.bank.mini_bank_api.domain.DTO.CustomerDTO;
import com.lucas.bank.mini_bank_api.domain.entity.Account;
import com.lucas.bank.mini_bank_api.repository.AccountRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AccountService {

    @Autowired
    private final AccountRepository accountRepository;

    public Account create(@Valid AccountDTO accountDTO) {
        if(accountRepository.existsByAccountNumber(accountDTO.getAccountNumber())){
            throw new RuntimeException("Account number already exists");
        }
        Account account = Account.builder()
                .accountNumber(accountDTO.getAccountNumber())
                .balance(accountDTO.getBalance())
                .active(accountDTO.getActive())
                .createdAt(accountDTO.getCreatedAt())
                .build();

        return  accountRepository.save(account);
    }


    public List<Account> findAll() {
        return  accountRepository.findAll();
    }
}
