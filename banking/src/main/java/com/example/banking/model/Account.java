package com.example.banking.model;

import java.util.concurrent.atomic.AtomicLong;

public class Account {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1000000);

    private final Long userId;
    private String name;
    private double balance;

    public Account(String name, double initialBalance) {
        this.userId = ID_GENERATOR.getAndIncrement();
        this.name = name;
        this.balance = initialBalance;
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

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}