package com.example.BankSystem.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BankSystem.dto.DepositRequestDTO;
import com.example.BankSystem.dto.TransactionResponseDTO;
import com.example.BankSystem.dto.TransferRequestDTO;
import com.example.BankSystem.dto.WithdrawRequestDTO;
import com.example.BankSystem.exception.ApiResponsee;
import com.example.BankSystem.interfaces.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(
        name = "Transaction API",
        description = "APIs for deposits, withdrawals, transfers and transaction history"
)
@SecurityRequirement(name = "Bearer Authentication")
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(
            summary = "Deposit Money",
            description = "Deposits money into a bank account."
    )
    @PostMapping("/transactions/deposit")
    public ResponseEntity<ApiResponsee<TransactionResponseDTO>> deposit(
            @Valid @RequestBody DepositRequestDTO depositRequestDTO) {

        TransactionResponseDTO response =
                transactionService.deposit(depositRequestDTO);

        ApiResponsee<TransactionResponseDTO> apiResponse =
                new ApiResponsee<>(
                        true,
                        "Money deposited successfully",
                        response
                );

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(
            summary = "Withdraw Money",
            description = "Withdraws money from the authenticated user's bank account."
    )
    @PostMapping("/transactions/withdraw")
    public ResponseEntity<ApiResponsee<TransactionResponseDTO>> withdraw(
            @Valid @RequestBody WithdrawRequestDTO withdrawRequestDTO,
            Authentication authentication) {

        TransactionResponseDTO response =
                transactionService.withdraw(
                        withdrawRequestDTO,
                        authentication.getName()
                );

        ApiResponsee<TransactionResponseDTO> apiResponse =
                new ApiResponsee<>(
                        true,
                        "Money withdrawn successfully",
                        response
                );

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(
            summary = "Transfer Money",
            description = "Transfers money from the authenticated user's account to another account."
    )
    @PostMapping("/transactions/transfer")
    public ResponseEntity<ApiResponsee<TransactionResponseDTO>> transfer(
            @Valid @RequestBody TransferRequestDTO transferRequestDTO,
            Authentication authentication) {

        TransactionResponseDTO response =
                transactionService.transfer(
                        transferRequestDTO,
                        authentication.getName()
                );

        ApiResponsee<TransactionResponseDTO> apiResponse =
                new ApiResponsee<>(
                        true,
                        "Money transferred successfully",
                        response
                );

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(
            summary = "Get Account Transaction History",
            description = "Returns all transactions associated with the specified account number."
    )
    @GetMapping("/accounts/{accountNumber}/transactions")
    public ResponseEntity<ApiResponsee<List<TransactionResponseDTO>>> getAccountHistory(

            @Parameter(
                    description = "Account number",
                    example = "123456789012"
            )
            @PathVariable String accountNumber,

            Authentication authentication) {

        List<TransactionResponseDTO> response =
                transactionService.getAccountTransactionHistory(
                        accountNumber,
                        authentication
                );

        ApiResponsee<List<TransactionResponseDTO>> apiResponse =
                new ApiResponsee<>(
                        true,
                        "Account transaction history fetched successfully",
                        response
                );

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(
            summary = "Get User Transaction History",
            description = "Returns the transaction history associated with the specified user."
    )
    @GetMapping("/users/{userId}/transactions")
    public ResponseEntity<ApiResponsee<List<TransactionResponseDTO>>> getUserAccountHistory(

            @Parameter(
                    description = "Unique ID of the user",
                    example = "1"
            )
            @PathVariable Long userId) {

        List<TransactionResponseDTO> response =
                transactionService.getUserTransactionHistory(userId);

        ApiResponsee<List<TransactionResponseDTO>> apiResponse =
                new ApiResponsee<>(
                        true,
                        "User transaction history fetched successfully",
                        response
                );

        return ResponseEntity.ok(apiResponse);
    }
}