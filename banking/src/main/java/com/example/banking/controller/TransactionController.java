package com.example.banking.controller;

import com.example.banking.dto.TransactionRequest;
import com.example.banking.dto.TransactionResponse;
import com.example.banking.model.Transaction;
import com.example.banking.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Transfer funds between accounts",
            description = "Rate limited to 5 requests per minute per IP (higher security)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transfer successful",
                    headers = {
                            @Header(name = "X-RateLimit-Limit", description = "Max requests per minute"),
                            @Header(name = "X-RateLimit-Remaining", description = "Remaining requests")
                    }),
            @ApiResponse(responseCode = "400", description = "Invalid transfer request"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "429", description = "Rate limit exceeded")
    })
    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transferFunds(@Valid @RequestBody TransactionRequest request) {
        TransactionResponse response = transactionService.transferFunds(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get transaction history",
            description = "Rate limited to 20 requests per minute per IP")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transaction history retrieved",
                    headers = {
                            @Header(name = "X-RateLimit-Limit", description = "Max requests per minute"),
                            @Header(name = "X-RateLimit-Remaining", description = "Remaining requests")
                    }),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "429", description = "Rate limit exceeded")
    })
    @GetMapping("/history/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable Long accountId) {
        List<Transaction> transactions = transactionService.getTransactionHistory(accountId);
        return ResponseEntity.ok(transactions);
    }
}