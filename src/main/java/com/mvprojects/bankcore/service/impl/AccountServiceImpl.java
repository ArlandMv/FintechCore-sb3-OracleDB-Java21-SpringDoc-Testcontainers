package com.mvprojects.bankcore.service.impl;

import com.mvprojects.bankcore.dto.AccountDto;
import com.mvprojects.bankcore.entity.Account;
import com.mvprojects.bankcore.mapper.AccountMapper;
import com.mvprojects.bankcore.repository.AccountRepository;
import com.mvprojects.bankcore.service.AccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
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

    @Override
    public AccountDto deposit(Long id, BigDecimal ammount) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()){
            Account existingAccount = account.get();
            BigDecimal newBalance = existingAccount.getBalance().add(ammount);
            existingAccount.setBalance(newBalance);
            Account savedAccount = accountRepository.save(existingAccount);
            return AccountMapper.maptoAccountDto(savedAccount);
        } else { throw new NoSuchElementException("Cuenta "+id+" no existe"); }
    }

    @Override
    public AccountDto withdraw(Long id, BigDecimal ammount) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if(accountOptional.isPresent()){
            Account account = accountOptional.get();

            if (ammount.compareTo(account.getBalance())>0){
                throw new IllegalArgumentException("Fondos Insuficientes");
            }
            BigDecimal newBalance = account.getBalance().subtract(ammount);
            account.setBalance(newBalance);
            Account savedAccount = accountRepository.save(account);
            return AccountMapper.maptoAccountDto(savedAccount);
        } else {
            throw new NoSuchElementException("Cuenta "+id+" no existe");
        }
    }

}
