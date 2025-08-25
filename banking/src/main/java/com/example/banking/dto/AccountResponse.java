package com.example.banking.dto;

public class AccountResponse {
    private Long userId;
    private String name;
    private double balance;

    public AccountResponse(Long userId, String name, double balance) {
        this.userId = userId;
        this.name = name;
        this.balance = balance;
    }

    //Getters
    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }
}