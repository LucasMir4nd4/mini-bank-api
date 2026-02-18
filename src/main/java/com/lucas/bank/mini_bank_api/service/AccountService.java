package com.lucas.bank.mini_bank_api.service;

import com.lucas.bank.mini_bank_api.domain.DTO.AccountRequestDTO;
import com.lucas.bank.mini_bank_api.domain.DTO.AccountResponseDTO;
import com.lucas.bank.mini_bank_api.domain.DTO.CustomerIdDTO;
import com.lucas.bank.mini_bank_api.domain.entity.Account;
import com.lucas.bank.mini_bank_api.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    // ==========================
    // CONVERTER ENTITY -> RESPONSE
    // ==========================
    private AccountResponseDTO toResponse(Account account) {
        return new AccountResponseDTO(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getActive(),
                account.getCreatedAt(),
                new CustomerIdDTO(account.getCustomer().getId()));
    }

    // ==========================
    // CREATE
    // ==========================
    public AccountResponseDTO create(AccountRequestDTO request) {

        if (accountRepository.existsByAccountNumber(request.accountNumber())) {
            throw new RuntimeException("Account number already exists");
        }

        Account account = Account.builder()
                .accountNumber(request.accountNumber())
                .balance(request.balance())
                .active(true)
                .build();

        Account saved = accountRepository.save(account);

        return toResponse(saved);
    }

    // ==========================
    // FIND ALL
    // ==========================
    public List<AccountResponseDTO> findAll() {
        return accountRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }
}
