package com.lucas.bank.mini_bank_api.repository;

import com.lucas.bank.mini_bank_api.domain.entity.Account;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Override
    @NullMarked
    Optional<Account> findById(Long aLong);

    Optional<Account> findByAccountNumber(String numberAccount);

    boolean existsByAccountNumber(String numberAccount);
}
