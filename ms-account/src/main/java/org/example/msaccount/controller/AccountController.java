package org.example.msaccount.controller;

import org.example.msaccount.domain.Account;
import org.example.msaccount.domain.AccountTransaction;
import org.example.msaccount.domain.AccountType;
import org.example.msaccount.service.AccountService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.netflix.appinfo.AmazonInfo.MetaDataKey.accountId;

@RestController
public class AccountController {

    private AccountService accountService ;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

   @PostMapping("/accounts/create")
    public Account createNewAccount(double initialBalance, AccountType accountType, String currency, Long customerId) {
        return accountService.createNewAccount(initialBalance, accountType, currency, customerId);
    }

    @PostMapping("/accounts/credit")
    public AccountTransaction creditAccount(String accountId, Double amount) {
        return accountService.creditAccount(accountId, amount);
    }

    @PostMapping("/accounts/debit")
    public AccountTransaction debitAccount(String accountId, Double amount) {
        return accountService.debitAccount(accountId, amount);
    }

    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/accounts/{accountId}")
    public Account getAccountById(@PathVariable String accountId) {
        return accountService.getAccountById(accountId);
    }

}
