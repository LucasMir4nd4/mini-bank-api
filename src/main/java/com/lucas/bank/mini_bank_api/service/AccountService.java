package com.lucas.bank.mini_bank_api.service;

import com.lucas.bank.mini_bank_api.domain.DTO.AccountRequestDTO;
import com.lucas.bank.mini_bank_api.domain.DTO.AccountResponseDTO;
import com.lucas.bank.mini_bank_api.domain.DTO.CustomerIdDTO;
import com.lucas.bank.mini_bank_api.domain.entity.Account;
import com.lucas.bank.mini_bank_api.domain.entity.Customer;
import com.lucas.bank.mini_bank_api.repository.AccountRepository;
import com.lucas.bank.mini_bank_api.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    private final SecureRandom random = new SecureRandom();


    private String generateAccountNumber() {
        // 8 dígitos (10000000 a 99999999)
        return String.valueOf(10_000_000 + random.nextInt(90_000_000));
    }

    private String generateUniqueAccountNumber() {
        String number;
        int attempts = 0;

        do {
            if (++attempts > 20) {
                throw new IllegalStateException("Could not generate unique account number");
            }
            number = generateAccountNumber();
        } while (accountRepository.existsByAccountNumber(number));

        return number;
    }

    private AccountResponseDTO toResponse(Account account) {
        return new AccountResponseDTO(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getActive(),
                account.getCreatedAt(),
                new CustomerIdDTO(account.getCustomer().getId()));
    }

    @Transactional
    public AccountResponseDTO create(AccountRequestDTO request) {

        Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Account account = Account.builder()
                .accountNumber(generateUniqueAccountNumber())
                .balance(BigDecimal.valueOf(0.0))
                .active(true)
                .customer(customer)
                .build();

        Account saved = accountRepository.save(account);

        return toResponse(saved);
    }


    public List<AccountResponseDTO> findAll() {
        return accountRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }
}
