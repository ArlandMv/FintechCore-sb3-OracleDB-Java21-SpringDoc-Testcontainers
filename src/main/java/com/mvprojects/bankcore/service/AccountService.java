package com.mvprojects.bankcore.service;

import com.mvprojects.bankcore.dto.AccountDto;
import com.mvprojects.bankcore.entity.Account;

public interface AccountService {
    AccountDto createAccount(AccountDto accountdto);
}
