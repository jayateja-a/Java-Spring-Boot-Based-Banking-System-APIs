package com.example.banking.controller;

import com.example.banking.dto.AccountCreationRequest;
import com.example.banking.dto.AccountResponse;
import com.example.banking.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "Create a new bank account",
            description = "Rate limited to 10 requests per minute per IP")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Account created successfully",
                    headers = {
                            @Header(name = "X-RateLimit-Limit", description = "Max requests per minute"),
                            @Header(name = "X-RateLimit-Remaining", description = "Remaining requests")
                    }),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "429", description = "Rate limit exceeded")
    })
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountCreationRequest request) {
        AccountResponse response = accountService.createAccount(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get account details",
            description = "Rate limited to 10 requests per minute per IP")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account details retrieved",
                    headers = {
                            @Header(name = "X-RateLimit-Limit", description = "Max requests per minute"),
                            @Header(name = "X-RateLimit-Remaining", description = "Remaining requests")
                    }),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "429", description = "Rate limit exceeded")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable Long userId) {
        AccountResponse response = accountService.getAccount(userId);
        return ResponseEntity.ok(response);
    }
}