package com.mvprojects.bankcore.mapper;

import com.mvprojects.bankcore.dto.AccountDto;
import com.mvprojects.bankcore.entity.Account;

public class AccountMapper {
    public static Account mapToAccount(AccountDto accountDto){
        return new Account(
                accountDto.getId(),
                accountDto.getHolderAccount(),
                accountDto.getBalance()
        );
    }

    public static AccountDto maptoAccountDto(Account account){
        return new AccountDto(
                account.getId(),
                account.getHolderName(),
                account.getBalance()
        );
    }
}
