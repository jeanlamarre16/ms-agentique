package org.example.msaccount;

import org.example.msaccount.domain.AccountType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.example.msaccount.service.AccountService;

@SpringBootApplication
public class MsAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsAccountApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(AccountService accountService){
        return args -> {
            accountService.createNewAccount(6500, AccountType.CURRENT_ACCOUNT, "AJL", Long.valueOf(1));
            accountService.createNewAccount(300, AccountType.SAVING_ACCOUNT, "AJL", Long.valueOf(1));
            accountService.createNewAccount(8575, AccountType.CURRENT_ACCOUNT, "Toto", Long.valueOf(2));

            accountService.getAllAccounts().forEach(account -> {
                    for (int i = 0; i < 5; i++ ) {
                        accountService.creditAccount(account.getId(), Math.random() * 150);
                        accountService.debitAccount(account.getId(), Math.random() * 154);
                    }
            });
        };
    }
}
