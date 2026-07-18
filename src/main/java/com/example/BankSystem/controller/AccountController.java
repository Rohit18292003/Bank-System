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
import com.example.BankSystem.inter.AccountService;
import com.example.BankSystem.service.AccountServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

	private final AccountService accountService;

	// Create
	@PostMapping("/{id}")
	public ResponseEntity<String> create(@RequestBody AccountRequestDTO requestDTO, @PathVariable Long id) {

		ResponseEntity<String> response = accountService.createAccount(requestDTO, id);
		return ResponseEntity.status(HttpStatus.CREATED).body(response.getBody());

	}

	// Get All
	@GetMapping("usersAccount/{userId}")
	public ResponseEntity<ResponseEntity<List<AccountResponseDTO>>> getUserAllAccount(@PathVariable Long userId) {

		ResponseEntity<List<AccountResponseDTO>> response = accountService.getUsersAllAccounts(userId);
		return ResponseEntity.ok(response);
	}

	// Get By Id
	@GetMapping("/{accountNum}")
	public ResponseEntity<ResponseEntity<AccountResponseDTO>> getByAccountNum(@PathVariable String accountNum) {

		ResponseEntity<AccountResponseDTO> response = accountService.getAccountByAccountNumber(accountNum);
		return ResponseEntity.ok(response);
	}

	// Update (Replace Entire Resource)
	@PutMapping("/{accountNum}")
	public ResponseEntity<ResponseEntity<String>> update(@PathVariable String accountNum,
			@RequestBody AccountRequestDTO requestDTO) {

		ResponseEntity<String> response = accountService.updateUserAccountStatusByNumber(accountNum, requestDTO);
		return ResponseEntity.ok(response);
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
