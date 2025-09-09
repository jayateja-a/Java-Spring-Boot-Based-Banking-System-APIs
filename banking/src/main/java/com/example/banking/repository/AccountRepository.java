package com.example.banking.repository;

import com.example.banking.model.Account;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class AccountRepository {
    private final Map<Long, Account> accounts = new ConcurrentHashMap<>();

    public Account save(Account account) {
        accounts.put(account.getUserId(), account);
        return account;
    }

    public Optional<Account> findById(Long userId) {
        return Optional.ofNullable(accounts.get(userId));
    }

    public boolean existsById(Long userId) {
        return accounts.containsKey(userId);
    }
}