package com.example.BankSystem.controller;

import java.util.List;

import com.example.BankSystem.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.example.BankSystem.interfaces.AccountService;

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
            @PathVariable Long userId ,Authentication authentication) {

       AccountResponseDTO response = accountService.createAccount(requestDTO, userId,authentication);

        ApiResponse<AccountResponseDTO> apiResponse =
                new ApiResponse<>(true, "Account created successfully", response);

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/admin/{userId}/accounts")
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
            @PathVariable String accountNumber ,@AuthenticationPrincipal CustomUserDetails userDetails) {


        AccountResponseDTO response =
                accountService.getAccountByAccountNumber(accountNumber , userDetails.getUsername());

        ApiResponse<AccountResponseDTO> apiResponse =
                new ApiResponse<>(true, "Account fetched successfully", response);

        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/accounts/{accountNumber}")
    public ResponseEntity<ApiResponse<AccountResponseDTO>> update(
            @PathVariable String accountNumber,
            @Valid @RequestBody AccountRequestDTO requestDTO ,@AuthenticationPrincipal CustomUserDetails userDetails ) {

        AccountResponseDTO response =
                accountService.updateAccountStatus(accountNumber, requestDTO, userDetails.getUsername());

        ApiResponse<AccountResponseDTO> apiResponse =
                new ApiResponse<>(true, "Account updated successfully", response);

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/accounts/{accountNumber}")
    public ResponseEntity<Void> delete(@PathVariable String accountNumber, @AuthenticationPrincipal CustomUserDetails userDetails) {

        accountService.deleteAccount(accountNumber , userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}