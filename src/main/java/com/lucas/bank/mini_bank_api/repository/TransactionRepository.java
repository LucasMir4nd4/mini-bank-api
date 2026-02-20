package com.lucas.bank.mini_bank_api.repository;

import com.lucas.bank.mini_bank_api.domain.entity.Transaction;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    @Override
    @NullMarked
    Optional<Transaction> findById(Long id);

    @Override
    List<Transaction> findAll();
}
