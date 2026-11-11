package org.example.msaccount.service;

import exception.AccountNotFoundException;
import exception.InsufficientBalanceException;
import org.example.msaccount.domain.*;
import org.example.msaccount.exception.NegativeAmountException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.msaccount.repository.AccountRepository;
import org.example.msaccount.repository.TransactionRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     *
     * @param initialBalance
     * @param accountType
     * @param currency
     * @param customerId
     * @return
     */
    @Transactional
    public Account createNewAccount(double initialBalance, AccountType accountType, String currency, Long customerId) {
        double cleanBalance = round(initialBalance);
        if (cleanBalance <= 0) throw new NegativeAmountException("Le montant doit être positif");

        // 1. Création et sauvegarde du compte
        Account account = Account.builder()
                .id(UUID.randomUUID().toString())
                .balance(cleanBalance)
                .accountType(accountType)
                .currency(currency)
                .accountStatus(AccountStatus.CREATED)
                .customerId(customerId)
                .createdAt(LocalDateTime.now())
                .build();
        Account newAccount = accountRepository.save(account);

        // 2. Création et sauvegarde de la transaction initiale (Crédit)
        AccountTransaction accountTransaction = AccountTransaction.builder()
                .dateTime(LocalDateTime.now())
                .transactionType(TransactionType.CREDIT)
                .amount(cleanBalance)
                .account(newAccount)
                .build();

        transactionRepository.save(accountTransaction);

        return newAccount;
    }

    /**
     *
     * @param accountId
     * @param amount
     * @return
     */
    @Transactional
    public AccountTransaction creditAccount(String accountId, Double amount) {
        double cleanAmount = round(amount);
        if (cleanAmount <= 0) {
            throw new NegativeAmountException("Amount should be positive");
        }
        Account account = getAccountById(accountId);

        double newBalance = BigDecimal.valueOf(account.getBalance())
                .add(BigDecimal.valueOf(cleanAmount))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        account.setBalance(newBalance);
        Account creditedAccount = accountRepository.save(account);

        // enregistrement de la transaction associée à l'opération de Crédit
        AccountTransaction accountTransaction = AccountTransaction.builder()
                .account(creditedAccount)
                .amount(cleanAmount)
                .dateTime(LocalDateTime.now())
                .transactionType(TransactionType.CREDIT)
                .build();
        return transactionRepository.save(accountTransaction);
    }

    /**
     *
     * @param accountId
     * @param amount
     * @return
     */
    @Transactional
    public AccountTransaction debitAccount(String accountId, Double amount) {
        double cleanAmount = round(amount);
        if (cleanAmount <= 0) {
            throw new NegativeAmountException("Amount should be positive");
        }

        // Récupération du compte pour vérification
        Account account = getAccountById(accountId);

        if(account.getBalance() < cleanAmount) {
            throw new InsufficientBalanceException("Solde insuffisant !");
        }

        double newBalance = BigDecimal.valueOf(account.getBalance())
                .subtract(BigDecimal.valueOf(cleanAmount))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        account.setBalance(newBalance);
        Account debitedAccount = accountRepository.save(account);

        // enregistrement de la transaction associée à l'opération de débit
        AccountTransaction accountTransaction = AccountTransaction.builder()
                .account(debitedAccount)
                .amount(cleanAmount)
                .dateTime(LocalDateTime.now())
                .transactionType(TransactionType.DEBIT)
                .build();
        return transactionRepository.save(accountTransaction);
    }

    /**
     *
     * @return
     */
    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    /**
     *
     * @param accountId
     * @return
     */
    public Account getAccountById(String accountId) {
        return accountRepository.findById(accountId).orElseThrow(()-> new AccountNotFoundException("Account not found  !"));
    }

    /**
     * Méthode utilitaire pour arrondir à 2 décimales
     */
    private double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}