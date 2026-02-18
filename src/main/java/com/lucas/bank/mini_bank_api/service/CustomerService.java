package com.lucas.bank.mini_bank_api.service;

import com.lucas.bank.mini_bank_api.domain.DTO.AccountResponseDTO;
import com.lucas.bank.mini_bank_api.domain.DTO.CustomerIdDTO;
import com.lucas.bank.mini_bank_api.domain.DTO.CustomerRequestDTO;
import com.lucas.bank.mini_bank_api.domain.DTO.CustomerResponseDTO;
import com.lucas.bank.mini_bank_api.domain.entity.Account;
import com.lucas.bank.mini_bank_api.domain.entity.Customer;
import com.lucas.bank.mini_bank_api.repository.AccountRepository;
import com.lucas.bank.mini_bank_api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    @Autowired
    private final CustomerRepository customerRepository;

    @Autowired
    private final AccountRepository accountRepository;

    private CustomerResponseDTO toResponse(Customer customer) {

        List<AccountResponseDTO> accounts = customer.getAccounts()
                .stream()
                .map(account -> new AccountResponseDTO(
                        account.getId(),
                        account.getAccountNumber(),
                        account.getBalance(),
                        account.getActive(),
                        account.getCreatedAt(),
                        new CustomerIdDTO(account.getCustomer().getId())
                ))
                .toList();

        return new CustomerResponseDTO(
                customer.getId(),
                customer.getName(),
                customer.getCpf(),
                customer.getEmail(),
                customer.getCreatedAt(),
                accounts
        );
    }


    private AccountResponseDTO accountToResponse(Account account) {

        return new AccountResponseDTO(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getActive(),
                account.getCreatedAt(),
                new CustomerIdDTO(account.getCustomer().getId()));
    }

    public CustomerResponseDTO create(CustomerRequestDTO request) {

        Customer customer = Customer.builder()
                .name(request.name())
                .cpf(request.cpf())
                .email(request.email())
                .password(request.password())
                .build();

        Customer saved = customerRepository.save(customer);

        return toResponse(saved);
    }

    public List<CustomerResponseDTO> findAll() {
        return customerRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public Optional<AccountResponseDTO> findByCustomer(CustomerResponseDTO customerDTO) {
        return accountRepository.findByCustomer(customerDTO).map(this::accountToResponse);
    }


    public Optional<CustomerResponseDTO> findById(Long id) {

        return customerRepository.findById(id)
                .map(this::toResponse);
    }
}
