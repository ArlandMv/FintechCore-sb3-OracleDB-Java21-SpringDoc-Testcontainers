package com.mvprojects.bankcore.service.impl;

import com.mvprojects.bankcore.dto.AccountDto;
import com.mvprojects.bankcore.entity.Account;
import com.mvprojects.bankcore.mapper.AccountMapper;
import com.mvprojects.bankcore.repository.AccountRepository;
import com.mvprojects.bankcore.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    @Override
    public AccountDto createAccount(AccountDto accountdto) {
        Account account = AccountMapper.mapToAccount(accountdto);
        accountRepository.save(account);
        return AccountMapper.maptoAccountDto(account);
        
    }

    @Override
    public Optional<AccountDto> getAccountById(Long id) {
        return accountRepository.findById(id)
                .map(AccountMapper::maptoAccountDto);
    }
}
