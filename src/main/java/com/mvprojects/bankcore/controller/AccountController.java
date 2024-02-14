package com.mvprojects.bankcore.controller;

import com.mvprojects.bankcore.dto.AccountDto;
import com.mvprojects.bankcore.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private AccountService accountService;
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
}
