package com.example.banking.model;

import java.time.LocalDateTime;

public class Transaction {
    private Long fromAccount;
    private Long toAccount;
    private double amount;
    private LocalDateTime timestamp;

    public Transaction(Long fromAccount, Long toAccount, double amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    //Getters
    public Long getFromAccount() {
        return fromAccount;
    }

    public Long getToAccount() {
        return toAccount;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}