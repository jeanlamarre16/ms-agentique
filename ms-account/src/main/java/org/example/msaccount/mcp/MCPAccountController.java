package org.example.msaccount.mcp;

import org.example.msaccount.domain.Account;
import org.example.msaccount.domain.AccountTransaction;
import org.example.msaccount.domain.AccountType;
import org.example.msaccount.service.AccountService;
import org.springframework.ai.mcp.annotation.McpArg;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MCPAccountController {

    private AccountService accountService;

    public MCPAccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @McpTool(description = "Create a new account")
    public Account createNewAccount(@McpArg(description = "The initial balance") double initialBalance,
                                    @McpArg(description = "The type of account") AccountType accountType,
                                    @McpArg(description = "The currency of account") String currency,
                                    @McpArg(description = "The customer id") Long customerId) {
        return accountService.createNewAccount(initialBalance, accountType, currency, customerId);
    }

    @McpTool(description = "Credit an account by id")
    public AccountTransaction creditAccount(@McpArg(description = "The account id") String accountId, @McpArg(description = "The amount to credit") double amount) {
        return accountService.creditAccount(accountId, amount);
    }

    @McpTool(description = "Save new account")
    public AccountTransaction debitAccount(@McpArg(description = "The account id") String accountId, @McpArg(description = "the amount to debit") double amount) {
        return accountService.debitAccount(accountId, amount);
    }

    @McpTool(description = "Get all accounts")
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @McpTool(description = "Get account by ID")
    public Account getAccountById(@McpArg(description = "The account id") String accountId) {
        return accountService.getAccountById(accountId);
    }
}
