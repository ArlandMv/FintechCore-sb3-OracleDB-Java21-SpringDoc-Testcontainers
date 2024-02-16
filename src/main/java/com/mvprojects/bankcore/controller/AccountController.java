package com.mvprojects.bankcore.controller;

import com.mvprojects.bankcore.dto.AccountDto;
import com.mvprojects.bankcore.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Add Account REST API
    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto){
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    // Get Account REST API
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id){
        Optional<AccountDto> accountDto = accountService.getAccountById(id);
        if (accountDto.isPresent()){
            return ResponseEntity.ok(accountDto.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update up Account REST API
    @PostMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long id, @RequestBody BigDecimal amount) {
        try {
            AccountDto updatedAccount = accountService.deposit(id, amount);
            return ResponseEntity.ok(updatedAccount);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Update up Account REST API
    @PostMapping("/{id}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable Long id, @RequestBody BigDecimal amount) {
        try {
            AccountDto updatedAccount = accountService.withdraw(id, amount);
            return ResponseEntity.ok(updatedAccount);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Fondos Insuficientes");
        }
    }
}
