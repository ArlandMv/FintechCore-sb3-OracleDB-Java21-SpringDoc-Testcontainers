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
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
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
        // Act
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

    @Test
    @DisplayName("Deposit should Succeed")
    void givenValidAccountIdAndDepositAmount_whenDeposit_thenUpdatedAccountDtoReturned() {
        // Arrange
        Long accountId = mockAccountDto.getId();
        BigDecimal depositAmount = BigDecimal.valueOf(500.0);
        BigDecimal expectedBalance = mockAccountDto.getBalance().add(depositAmount);
        given(accountService.deposit(accountId, depositAmount)).willReturn(new AccountDto(accountId,mockAccountDto.getHolderAccount(),expectedBalance));
        // Act
        ResponseEntity<AccountDto> response = accountController.deposit(accountId, depositAmount);
        // Assert
        AccountDto updatedAccountDto = response.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedAccountDto.getBalance(), expectedBalance );
    }

    @Test
    @DisplayName("Deposit should Fail")
    void givenNonExistingAccountIdAndDepositAmount_whenDeposit_thenNotFoundStatusReturned() {
        // Arrange
        Long nonExistingId = 999L;
        BigDecimal depositAmount = BigDecimal.valueOf(500.0);
        given(accountService.deposit(nonExistingId, depositAmount)).willThrow(NoSuchElementException.class);
        // Act
        ResponseEntity<AccountDto> response = accountController.deposit(nonExistingId, depositAmount);
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Given existing account with sufficient balance, when withdraw is requested, then return updated account")
    void withdraw_ExistingAccountSufficientBalance_ReturnUpdatedAccount() {
        // Given
        Long accountId = 1L;
        BigDecimal amount = BigDecimal.valueOf(500.0);
        AccountDto updatedAccountDto = new AccountDto(accountId, "1234567890", BigDecimal.valueOf(500.0));
        given(accountService.withdraw(accountId, amount)).willReturn(updatedAccountDto);

        // When
        ResponseEntity<?> response = accountController.withdraw(accountId, amount);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedAccountDto, response.getBody());
    }

    @Test
    @DisplayName("Given existing account with insufficient balance, when withdraw is requested, then return bad request")
    void withdraw_ExistingAccountInsufficientBalance_ReturnBadRequest() {
        // Given
        Long accountId = 1L;
        BigDecimal amount = BigDecimal.valueOf(1500.0);
        given(accountService.withdraw(accountId, amount)).willThrow(new IllegalArgumentException("Insufficient balance"));
        // When
        ResponseEntity<?> response = accountController.withdraw(accountId, amount);
        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Fondos Insuficientes", response.getBody());
    }

    @Test
    @DisplayName("Given non-existing account, when withdraw is requested, then return not found")
    void withdraw_NonExistingAccount_ReturnNotFound() {
        // Given
        Long accountId = 999L;
        BigDecimal amount = BigDecimal.valueOf(500.0);
        given(accountService.withdraw(accountId, amount)).willThrow(new NoSuchElementException("Cuenta " + accountId + " no existe"));
        // When
        ResponseEntity<?> response = accountController.withdraw(accountId, amount);
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
