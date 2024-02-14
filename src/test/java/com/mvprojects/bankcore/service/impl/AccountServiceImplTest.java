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
    @DisplayName("createAccount Success")
    void givenNonExistingAccount_whenCreateAccount_thenAccountShouldBeReturned(){
        // Arrange
        Account account = AccountMapper.mapToAccount(accountDTO);
        given(accountRepository.save(any(Account.class))).willReturn(account);
        // Act when(accountRepository.save(any(Account.class))).thenReturn(account);
        AccountDto createdObject = accountService.createAccount(accountDTO);
        // Assert
        assertNotNull(createdObject);
        assertEquals(accountDTO.getBalance(), createdObject.getBalance());
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
        // Act
        Optional<AccountDto> resultOptional = accountService.getAccountById(nonExistingId);
        // Then
        assertTrue(resultOptional.isEmpty());
    }
}