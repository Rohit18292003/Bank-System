package com.example.BankSystem.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BankSystem.dto.AccountRequestDTO;
import com.example.BankSystem.dto.AccountResponseDTO;
import com.example.BankSystem.exception.ApiResponse;
import com.example.BankSystem.inter.AccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/users/{userId}/accounts")
    public ResponseEntity<ApiResponse<AccountResponseDTO>> create(
            @Valid @RequestBody AccountRequestDTO requestDTO,
            @PathVariable Long userId) {

       AccountResponseDTO response = accountService.createAccount(requestDTO, userId);

        ApiResponse<AccountResponseDTO> apiResponse =
                new ApiResponse<>(true, "Account created successfully", response);

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/users/{userId}/accounts")
    public ResponseEntity<ApiResponse<List<AccountResponseDTO>>> getUserAllAccount(
            @PathVariable Long userId) {

        List<AccountResponseDTO> response =
                accountService.getUsersAllAccounts(userId);

        ApiResponse<List<AccountResponseDTO>> apiResponse =
                new ApiResponse<>(true, "User accounts fetched successfully", response);

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/accounts/{accountNumber}")
    public ResponseEntity<ApiResponse<AccountResponseDTO>> getByAccountNum(
            @PathVariable String accountNumber) {

        AccountResponseDTO response =
                accountService.getAccountByAccountNumber(accountNumber);

        ApiResponse<AccountResponseDTO> apiResponse =
                new ApiResponse<>(true, "Account fetched successfully", response);

        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/accounts/{accountNumber}")
    public ResponseEntity<ApiResponse<AccountResponseDTO>> update(
            @PathVariable String accountNumber,
            @Valid @RequestBody AccountRequestDTO requestDTO) {

        AccountResponseDTO response =
                accountService.updateAccountStatus(accountNumber, requestDTO);

        ApiResponse<AccountResponseDTO> apiResponse =
                new ApiResponse<>(true, "Account updated successfully", response);

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/accounts/{accountNumber}")
    public ResponseEntity<Void> delete(@PathVariable String accountNumber) {

        accountService.deleteAccount(accountNumber);
        return ResponseEntity.noContent().build();
    }
}