package com.mvprojects.bankcore.service.impl;

import com.mvprojects.bankcore.dto.AccountDto;
import com.mvprojects.bankcore.entity.Account;
import com.mvprojects.bankcore.mapper.AccountMapper;
import com.mvprojects.bankcore.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private AccountServiceImpl accountService;
    private AccountDto accountDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        accountDTO = new AccountDto(null, "1234567890", BigDecimal.valueOf(1000.0));
    }

    @Test
    @DisplayName("create Account unit test")
    void createAccount(){
        // Arrange
        Account account = AccountMapper.mapToAccount(accountDTO);
        given(accountRepository.save(any(Account.class))).willReturn(account);
        // Act when(accountRepository.save(any(Account.class))).thenReturn(account);
        AccountDto createdObject = accountService.createAccount(accountDTO);
        // Assert
        assertNotNull(createdObject);
        assertEquals(accountDTO.getBalance(), createdObject.getBalance());
    }
}