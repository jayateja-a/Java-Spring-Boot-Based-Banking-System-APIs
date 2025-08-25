package com.example.banking.service;

import java.util.List;
import com.example.banking.model.Transaction;
import com.example.banking.dto.TransactionRequest;
import com.example.banking.dto.TransactionResponse;
import com.example.banking.exception.AccountNotFoundException;
import com.example.banking.exception.InsufficientFundsException;
import com.example.banking.exception.SameAccountTransferException;
import com.example.banking.model.Account;
import com.example.banking.repository.AccountRepository;
import com.example.banking.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(AccountRepository accountRepository,
                                  TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public TransactionResponse transferFunds(TransactionRequest request) {
        //Validate same account transfer
        if (request.getFromAccount().equals(request.getToAccount())) {
            throw new SameAccountTransferException(
                    "Cannot transfer from and to the same account: " + request.getFromAccount());
        }

        //Validate accounts exist
        Account fromAccount = accountRepository.findById(request.getFromAccount())
                .orElseThrow(() -> new AccountNotFoundException(
                        "Sender account not found with ID: " + request.getFromAccount()));

        Account toAccount = accountRepository.findById(request.getToAccount())
                .orElseThrow(() -> new AccountNotFoundException(
                        "Receiver account not found with ID: " + request.getToAccount()));

        //Validate sufficient funds
        if (fromAccount.getBalance() < request.getAmount()) {
            throw new InsufficientFundsException(
                    "Insufficient funds in account " + fromAccount.getUserId() +
                            ". Current balance: " + fromAccount.getBalance());
        }

        //Perform transfer
        fromAccount.setBalance(fromAccount.getBalance() - request.getAmount());
        toAccount.setBalance(toAccount.getBalance() + request.getAmount());

        //Record transaction
        Transaction transaction = new Transaction(
                request.getFromAccount(),
                request.getToAccount(),
                request.getAmount());
        transactionRepository.save(transaction);

        return new TransactionResponse(
                request.getFromAccount(),
                request.getToAccount(),
                request.getAmount(),
                transaction.getTimestamp(),
                "COMPLETED");
    }

    @Override
    public List<Transaction> getTransactionHistory(Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new AccountNotFoundException("Account not found with ID: " + accountId);
        }
        return transactionRepository.findByAccountId(accountId);
    }
}