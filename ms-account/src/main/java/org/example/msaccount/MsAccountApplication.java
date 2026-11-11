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
            accountService.saveNewAccount(6500, AccountType.CURRENT_ACCOUNT, "AJL", Long.valueOf(1));
            accountService.saveNewAccount(300, AccountType.SAVING_ACCOUNT, "AJL", Long.valueOf(1));
            accountService.saveNewAccount(8575, AccountType.CURRENT_ACCOUNT, "Toto", Long.valueOf(2));
        };
    }
}
