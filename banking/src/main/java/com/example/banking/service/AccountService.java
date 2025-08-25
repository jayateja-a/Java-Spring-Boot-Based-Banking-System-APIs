package com.example.banking.service;

import com.example.banking.dto.AccountCreationRequest;
import com.example.banking.dto.AccountResponse;

public interface AccountService {
    AccountResponse createAccount(AccountCreationRequest request);
    AccountResponse getAccount(Long userId);
}