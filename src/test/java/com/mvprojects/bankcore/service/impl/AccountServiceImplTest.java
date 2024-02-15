package com.mvprojects.bankcore.service.impl;

import com.github.dockerjava.api.exception.NotFoundException;
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
import java.util.NoSuchElementException;
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
    private AccountDto mockAccountDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockAccountDTO = new AccountDto(null, "1234567890", BigDecimal.valueOf(1000.0));
    }

    @Test
    @DisplayName("createAccount Success")
    void givenNonExistingAccount_whenCreateAccount_thenAccountShouldBeReturned(){
        // Arrange
        Account account = AccountMapper.mapToAccount(mockAccountDTO);
        given(accountRepository.save(any(Account.class))).willReturn(account);
        // Act when(accountRepository.save(any(Account.class))).thenReturn(account);
        AccountDto createdObject = accountService.createAccount(mockAccountDTO);
        // Assert
        assertNotNull(createdObject);
        assertEquals(mockAccountDTO.getBalance(), createdObject.getBalance());
    }

    @Test
    @DisplayName("GetAccountById should Succeed")
    void testGetAccountById_ExistingAccount() {
        // Given
        Long accountId = 1L;
        Account account = new Account();
        account.setId(accountId);
        given(accountRepository.findById(accountId)).willReturn(Optional.of(account));

        // When
        Optional<AccountDto> resultOptional = accountService.getAccountById(accountId);

        // Then
        assertTrue(resultOptional.isPresent());
        AccountDto result = resultOptional.get();
        assertNotNull(result);
        assertEquals(accountId, result.getId());
    }

    @Test
    @DisplayName("GetAccountById should Fail")
    void testGetAccountById_NonExistingAccount() {
        // Given
        Long nonExistingId = 999L;
        given(accountRepository.findById(nonExistingId)).willReturn(Optional.empty());
        // When
        Optional<AccountDto> resultOptional = accountService.getAccountById(nonExistingId);
        // Then
        assertTrue(resultOptional.isEmpty());
    }

    @Test
    @DisplayName("Deposit should Succeed")
    void testDeposit_ValidAccount() {
        // Arrange
        AccountDto accountDto = mockAccountDTO;
        Long accountId = mockAccountDTO.getId();
        BigDecimal initialBalance = mockAccountDTO.getBalance();
        BigDecimal depositAmount = BigDecimal.valueOf(500.0);
        Account account = AccountMapper.mapToAccount(mockAccountDTO);

        given(accountRepository.findById(accountId)).willReturn(Optional.of(account));
        given(accountRepository.save(any(Account.class))).willReturn(account);
        // Act
        AccountDto updatedAccountDto = accountService.deposit(accountId, depositAmount);

        // Assert
        assertNotNull(updatedAccountDto);
        assertEquals(accountId, updatedAccountDto.getId());
        assertEquals(initialBalance.add(depositAmount), updatedAccountDto.getBalance());
    }
    @Test
    @DisplayName("Deposit should Fail")
    void testDeposit_NonExistingAccount() {
        // Arrange
        Long nonExistingId = 999L;
        BigDecimal depositAmount = BigDecimal.valueOf(500.0);
        given(accountRepository.findById(nonExistingId)).willReturn(Optional.empty());
        // Act and Assert
        assertThrows(NoSuchElementException.class, () -> {
            accountService.deposit(nonExistingId, depositAmount);
        });
    }
}