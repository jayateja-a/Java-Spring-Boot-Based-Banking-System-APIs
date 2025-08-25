package com.example.banking.repository;

import com.example.banking.model.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class TransactionRepository {
    private final Map<Long, List<Transaction>> transactions = new ConcurrentHashMap<>();

    public void save(Transaction transaction) {
        //Save for sender
        transactions.computeIfAbsent(transaction.getFromAccount(), k -> new ArrayList<>())
                .add(transaction);

        //Save for receiver (if different account)
        if (!transaction.getFromAccount().equals(transaction.getToAccount())) {
            transactions.computeIfAbsent(transaction.getToAccount(), k -> new ArrayList<>())
                    .add(transaction);
        }
    }

    public List<Transaction> findByAccountId(Long accountId) {
        return transactions.getOrDefault(accountId, new ArrayList<>());
    }
}