package com.mvprojects.bankcore.repository;

import com.mvprojects.bankcore.entity.Account;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findById(Long aLong);
    Optional<Account> findByHolderName(String holderName);
}
