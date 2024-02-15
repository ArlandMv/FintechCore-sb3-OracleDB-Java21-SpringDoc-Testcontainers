package com.mvprojects.bankcore.service;

import com.mvprojects.bankcore.dto.AccountDto;
import com.mvprojects.bankcore.entity.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountService {
    AccountDto createAccount(AccountDto accountdto);
    Optional<AccountDto> getAccountById(Long id);
    AccountDto deposit(Long id, BigDecimal ammount);

    //List<Product> getAllProducts();
    //Optional<Product> getProductById(Long id);
    //Product updateProduct(Product updatedProduct);
    //void deleteById(Long id);

}
