package com.example.banking.config;

import com.example.banking.repository.AccountRepository;
import com.example.banking.repository.TransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public AccountRepository accountRepository() {
        return new AccountRepository();
    }

    @Bean
    public TransactionRepository transactionRepository() {
        return new TransactionRepository();
    }
}