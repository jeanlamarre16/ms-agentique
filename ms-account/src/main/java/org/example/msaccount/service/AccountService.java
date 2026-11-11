package org.example.msaccount.service;

import org.example.msaccount.domain.Account;
import org.example.msaccount.domain.AccountTransaction;
import org.example.msaccount.domain.AccountType;
import org.example.msaccount.domain.TransactionType;
import org.example.msaccount.exception.NegativeAmountException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.msaccount.repository.AccountRepository;
import org.example.msaccount.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class AccountService {
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;


    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Account saveNewAccount(double initialBalance, AccountType accountType, String currency, Long customerId) {
        if (initialBalance <= 0 ) throw new NegativeAmountException("Le montant doit être positif");

        // 1. Création et sauvegarde du compte
        Account account = Account.builder()
                .id(UUID.randomUUID().toString())
                .balance(initialBalance)
                .accountType(accountType)
                .currency(currency)
                .customerId(customerId)
                .createdAt(LocalDateTime.now())
                .build();

        Account savedAccount = accountRepository.save(account);

        // 2. Création et sauvegarde de la transaction initiale (Crédit)
        AccountTransaction accountTransaction = AccountTransaction.builder()
                        .dateTime(LocalDateTime.now())
                        .transactionType(TransactionType.CREDIT)
                        .amount(initialBalance)
                        .account(savedAccount)
                        .build();

        transactionRepository.save(accountTransaction);

        return savedAccount;
    }

    //public Account
}
