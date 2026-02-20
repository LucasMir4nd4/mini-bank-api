package com.lucas.bank.mini_bank_api.service;

import com.lucas.bank.mini_bank_api.domain.DTO.TransactionRequestDTO;
import com.lucas.bank.mini_bank_api.domain.DTO.TransactionResponseDTO;
import com.lucas.bank.mini_bank_api.domain.entity.Account;
import com.lucas.bank.mini_bank_api.domain.entity.Transaction;
import com.lucas.bank.mini_bank_api.domain.entity.TransactionStatus;
import com.lucas.bank.mini_bank_api.repository.AccountRepository;
import com.lucas.bank.mini_bank_api.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private final AccountRepository accountRepository;

    @Autowired
    private final TransactionRepository transactionRepository;

    public TransactionService(AccountRepository accountRepository,
                              TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public TransactionResponseDTO tranfer(TransactionRequestDTO transactionRequestDTO) {

        validate(transactionRequestDTO);

        Account fromAccount = accountRepository.findById(transactionRequestDTO.fromAccountId())
                .orElseThrow(() -> new IllegalArgumentException("From account not found"));
        Account toAccount = accountRepository.findById(transactionRequestDTO.toAccountId())
                .orElseThrow(() -> new IllegalArgumentException("To account not found"));

        if (fromAccount.equals(toAccount)) {
            throw  new IllegalArgumentException(String.format("From account and To account are the same"));
        }

        BigDecimal amount = transactionRequestDTO.amount();

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds in the from account");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction newTransaction = new Transaction();
        newTransaction.setFromAccount(fromAccount);
        newTransaction.setToAccount(toAccount);
        newTransaction.setAmount(amount);
        newTransaction.setDescription(transactionRequestDTO.description());
        newTransaction.setStatus(TransactionStatus.SUCCESS);

        newTransaction = transactionRepository.save(newTransaction);

        return new TransactionResponseDTO(
                newTransaction.getId(),
                newTransaction.getFromAccount().getId(),
                newTransaction.getToAccount().getId(),
                newTransaction.getAmount(),
                newTransaction.getCreatedAt(),
                newTransaction.getStatus().name()
        );
    }

    private void validate(TransactionRequestDTO transactionRequest) {
        if (transactionRequest.fromAccountId() == null || transactionRequest.toAccountId() == null) {
            throw new IllegalArgumentException("fromAccountId and toAccountId are required" );
        }
        if (transactionRequest.amount() == null || transactionRequest.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount cannot be null or less than or equal to zero");
        }
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }
}
