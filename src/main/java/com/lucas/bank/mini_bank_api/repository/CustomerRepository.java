package com.lucas.bank.mini_bank_api.repository;

import com.lucas.bank.mini_bank_api.domain.entity.Customer;

import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Override
    @NullMarked
    Optional<Customer> findById(Long id);

    Optional<Customer> findByCpf(String cpf);

    Optional<Customer> findByEmail(String email);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
}
