package com.example.BankSystem.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
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
import com.example.BankSystem.dto.UserResponseDTO;
import com.example.BankSystem.exception.ApiResponse;
import com.example.BankSystem.inter.AccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

	private final AccountService accountService;

	// Create
	@PostMapping("/{id}")
	public ResponseEntity<ApiResponse<AccountResponseDTO>> create(@Valid @RequestBody AccountRequestDTO requestDTO, @PathVariable Long id) {

	 AccountResponseDTO response = accountService.createAccount(requestDTO, id);
	 ApiResponse<AccountResponseDTO> apiResponse = new ApiResponse<>(true, "Account created", response);
	return ResponseEntity.ok(apiResponse);

	}

	// Get All
	@GetMapping("usersAccount/{userId}")
	public ResponseEntity<ApiResponse<List<AccountResponseDTO>>> getUserAllAccount(@PathVariable Long userId) {

		List<AccountResponseDTO> response = accountService.getUsersAllAccounts(userId);
		ApiResponse<List<AccountResponseDTO>> apiResponse = new ApiResponse<>(true, "Get User All Accounts", response);
		return ResponseEntity.ok(apiResponse);
	}

	// Get By Id
	@GetMapping("/{accountNum}")
	public ResponseEntity<ApiResponse<AccountResponseDTO>> getByAccountNum(@PathVariable String accountNum) {

		AccountResponseDTO response = accountService.getAccountByAccountNumber(accountNum);
		ApiResponse<AccountResponseDTO> apiResponse = new ApiResponse<>(true, "Get Account by number", response);
		return ResponseEntity.ok(apiResponse);
	}

	// Update (Replace Entire Resource)
	@PutMapping("/{accountNum}")
	public ResponseEntity<ApiResponse<AccountResponseDTO>> update(@PathVariable String accountNum,
			@Valid @RequestBody AccountRequestDTO requestDTO) {

		 AccountResponseDTO response = accountService.updateAccountStatus(accountNum, requestDTO);
		 ApiResponse<AccountResponseDTO> apiResponse = new ApiResponse<>(true, "update Account Status", response);
		return ResponseEntity.ok(apiResponse);
	}

//    // Partial Update
//    @PatchMapping("/{id}")
//    public ResponseEntity<ExampleResponseDTO> partialUpdate(
//            @PathVariable Long id,
//            @RequestBody ExampleRequestDTO requestDTO) {
//
//        ExampleResponseDTO response = exampleService.partialUpdate(id, requestDTO);
//        return ResponseEntity.ok(response);
//    }

	// Delete
	@DeleteMapping("/delete/{accountNum}")
	public ResponseEntity<Void> delete(@PathVariable String accountNum) {

		accountService.deleteAccount(accountNum);
		return ResponseEntity.noContent().build();
	}

}
