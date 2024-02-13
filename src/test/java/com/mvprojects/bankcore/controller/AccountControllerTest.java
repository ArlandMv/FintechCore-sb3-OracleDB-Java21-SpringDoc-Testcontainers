package com.mvprojects.bankcore.controller;

import com.mvprojects.bankcore.dto.AccountDto;
import com.mvprojects.bankcore.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {
    @Mock
    private AccountServiceImpl accountService;

    @InjectMocks
    private AccountController accountController;

    @Test
    public void addAccount() {
        // Arrange
        String accountNumber = "1234567890";
        BigDecimal balance = BigDecimal.valueOf(1000.0);
        AccountDto accountDTO = new AccountDto(null, accountNumber, balance);
        AccountDto createdAccountDTO = new AccountDto(null, accountNumber, balance);
        when(accountService.createAccount(accountDTO)).thenReturn(createdAccountDTO);
        // Act
        ResponseEntity<AccountDto> response = accountController.addAccount(accountDTO);
        // Assert
        assertEquals(createdAccountDTO, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}

/*
@Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    private List<AccountDTO> dummyAccounts;

    @BeforeEach
    public void setUp() {
        // Prepare dummy accounts for testing
        AccountDTO account1 = new AccountDTO("1234567890", 1000.0);
        AccountDTO account2 = new AccountDTO("0987654321", 2000.0);
        dummyAccounts = Arrays.asList(account1, account2);
    }

    @Test
    public void testGetAllAccounts() {
        // Arrange
        when(accountService.getAllAccounts()).thenReturn(dummyAccounts);

        // Act
        List<AccountDTO> response = accountController.getAllAccounts();

        // Assert
        assertEquals(dummyAccounts.size(), response.size());
        assertEquals(dummyAccounts.get(0), response.get(0));
        assertEquals(dummyAccounts.get(1), response.get(1));
    }

    @Test
    public void testGetAccountById() {
        // Arrange
        Long accountId = 1L;
        AccountDTO dummyAccount = dummyAccounts.get(0);
        when(accountService.getAccountById(accountId)).thenReturn(Optional.of(dummyAccount));

        // Act
        ResponseEntity<AccountDTO> response = accountController.getAccountById(accountId);

        // Assert
        assertEquals(dummyAccount, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

 */
