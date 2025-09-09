package com.example.banking.service;

import com.example.banking.dto.AccountCreationRequest;
import com.example.banking.dto.AccountResponse;
import com.example.banking.exception.AccountNotFoundException;
import com.example.banking.model.Account;
import com.example.banking.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountResponse createAccount(AccountCreationRequest request) {
        Account account = new Account(request.getName(), request.getInitialBalance());
        account = accountRepository.save(account);
        return new AccountResponse(account.getUserId(), account.getName(), account.getBalance());
    }

    @Override
    public AccountResponse getAccount(Long userId) {
        Account account = accountRepository.findById(userId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + userId));
        return new AccountResponse(account.getUserId(), account.getName(), account.getBalance());
    }
}