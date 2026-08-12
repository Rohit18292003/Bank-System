package com.example.BankSystem.controller;

import java.util.List;

import com.example.BankSystem.security.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

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
import com.example.BankSystem.exception.ApiResponsee;

import com.example.BankSystem.interfaces.AccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Account API", description = "APIs for managing bank accounts")
public class AccountController {

    private final AccountService accountService;

    @Operation(
    	    summary = "Create Account",
    	    description = "Creates a new bank account for the specified user."
    	)
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Account created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/users/{userId}/accounts")
    public ResponseEntity<ApiResponsee<AccountResponseDTO>> create(
            @Valid @RequestBody AccountRequestDTO requestDTO,
            @Parameter(description = "User ID", example = "1")
            @PathVariable Long userId,
            Authentication authentication) {

       AccountResponseDTO response = accountService.createAccount(requestDTO, userId,authentication);

        ApiResponsee<AccountResponseDTO> apiResponse =
                new ApiResponsee<>(true, "Account created successfully", response);

        return ResponseEntity.ok(apiResponse);
    }

    
    @Operation(
    	    summary = "Get All Accounts",
    	    description = "Returns all accounts belonging to the specified user."
    	)
    	@ApiResponses({
    	    @ApiResponse(responseCode = "200", description = "Accounts fetched successfully"),
    	    @ApiResponse(responseCode = "404", description = "User not found")
    	})
    	@GetMapping("/admin/{userId}/accounts")
    public ResponseEntity<ApiResponsee<List<AccountResponseDTO>>> getUserAllAccount(
            @PathVariable Long userId) {

        List<AccountResponseDTO> response =
                accountService.getUsersAllAccounts(userId);

        ApiResponsee<List<AccountResponseDTO>> apiResponse =
                new ApiResponsee<>(true, "User accounts fetched successfully", response);

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(
    	    summary = "Get Account",
    	    description = "Returns the authenticated user's account by account number."
    	)
    	@ApiResponses({
    	    @ApiResponse(responseCode = "200", description = "Account fetched successfully"),
    	    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    	    @ApiResponse(responseCode = "404", description = "Account not found")
    	})
    @GetMapping("/accounts/{accountNumber}")
    public ResponseEntity<ApiResponsee<AccountResponseDTO>> getByAccountNum(
            @PathVariable String accountNumber ,@AuthenticationPrincipal CustomUserDetails userDetails) {


        AccountResponseDTO response =
                accountService.getAccountByAccountNumber(accountNumber , userDetails.getUsername());

        ApiResponsee<AccountResponseDTO> apiResponse =
                new ApiResponsee<>(true, "Account fetched successfully", response);

        return ResponseEntity.ok(apiResponse);
    }
    
    
    @Operation(
    	    summary = "Update Account",
    	    description = "Updates the account status of the authenticated user's account."
    	)
    	@ApiResponses({
    	    @ApiResponse(responseCode = "200", description = "Account updated successfully"),
    	    @ApiResponse(responseCode = "400", description = "Invalid request"),
    	    @ApiResponse(responseCode = "404", description = "Account not found")
    	})
    @PutMapping("/accounts/{accountNumber}")
    public ResponseEntity<ApiResponsee<AccountResponseDTO>> update(
            @PathVariable String accountNumber,
            @Valid @RequestBody AccountRequestDTO requestDTO ,@AuthenticationPrincipal CustomUserDetails userDetails ) {

        AccountResponseDTO response =
                accountService.updateAccountStatus(accountNumber, requestDTO, userDetails.getUsername());

        ApiResponsee<AccountResponseDTO> apiResponse =
                new ApiResponsee<>(true, "Account updated successfully", response);

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(
    	    summary = "Delete Account",
    	    description = "Deletes the authenticated user's account."
    	)
    	@ApiResponses({
    	    @ApiResponse(responseCode = "204", description = "Account deleted successfully"),
    	    @ApiResponse(responseCode = "404", description = "Account not found")
    	})
    @DeleteMapping("/accounts/{accountNumber}")
    public ResponseEntity<Void> delete(@PathVariable String accountNumber, @AuthenticationPrincipal CustomUserDetails userDetails) {

        accountService.deleteAccount(accountNumber , userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}