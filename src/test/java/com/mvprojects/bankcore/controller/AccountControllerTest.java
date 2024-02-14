package com.mvprojects.bankcore.controller;

import com.mvprojects.bankcore.dto.AccountDto;
import com.mvprojects.bankcore.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {
    @Mock
    private AccountServiceImpl accountService;

    @InjectMocks
    private AccountController accountController;

    private AccountDto mockAccountDto;

    @BeforeEach
    void setUp() {
        mockAccountDto  = new AccountDto(1L, "1234567890", BigDecimal.valueOf(1000.0));
    }


    @Test
    @DisplayName("AddAccount Success")
    public void givenValidAccountData_whenAddAccount_thenNewAccountShouldBeCreated() {
        // Arrange
        String accountNumber = "1234567890";
        BigDecimal balance = BigDecimal.valueOf(1000.0);
        AccountDto accountDTO = mockAccountDto ;
        AccountDto createdAccountDTO = new AccountDto(null, accountNumber, balance);
        when(accountService.createAccount(accountDTO)).thenReturn(createdAccountDTO);
        // Act
        ResponseEntity<AccountDto> response = accountController.addAccount(accountDTO);
        // Assert
        assertEquals(createdAccountDTO, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("GetAccountById Success")
    void givenExistingAccountId_whenGetAccountById_thenAccountShouldBeReturned() {
        // Given
        Long accountId = 1L;
        when(accountService.getAccountById(accountId)).thenReturn(Optional.of(mockAccountDto));
        // When
        ResponseEntity<AccountDto> response = accountController.getAccountById(accountId);
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockAccountDto, response.getBody());
    }

    @Test
    @DisplayName("GetAccountById NotFound")
    void givenNonExistingAccountId_whenGetAccountById_thenNotFoundStatusShouldBeReturned() {
        // Given
        Long nonExistingId = 999L;
        when(accountService.getAccountById(nonExistingId)).thenReturn(Optional.empty());
        // When
        ResponseEntity<AccountDto> response = accountController.getAccountById(nonExistingId);
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
