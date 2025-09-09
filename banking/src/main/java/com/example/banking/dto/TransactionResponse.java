package com.example.banking.dto;

import java.time.LocalDateTime;

public class TransactionResponse {
    private final Long fromAccount;
    private final Long toAccount;
    private final double amount;
    private final LocalDateTime timestamp;
    private final String status;

    public TransactionResponse(Long fromAccount, Long toAccount, double amount,
                               LocalDateTime timestamp, String status) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.timestamp = timestamp;
        this.status = status;
    }

    //Getters only (immutable DTO)
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

    public String getStatus() {
        return status;
    }
}