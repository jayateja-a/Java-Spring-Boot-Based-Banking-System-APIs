package com.example.banking.service;

import com.example.banking.dto.TransactionRequest;
import com.example.banking.dto.TransactionResponse;
import com.example.banking.model.Transaction;

import java.util.List;

public interface TransactionService {
    TransactionResponse transferFunds(TransactionRequest request);
    List<Transaction> getTransactionHistory(Long accountId);
}