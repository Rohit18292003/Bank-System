package com.example.BankSystem.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BankSystem.dto.AccountResponseDTO;
import com.example.BankSystem.dto.DepositRequestDTO;
import com.example.BankSystem.dto.TransactionResponseDTO;
import com.example.BankSystem.dto.TransferRequestDTO;
import com.example.BankSystem.dto.WithdrawRequestDTO;
import com.example.BankSystem.exception.ApiResponse;
import com.example.BankSystem.inter.TransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {

	private final TransactionService transactionService;
	
	
	@PostMapping("/deposit")
	public ResponseEntity<ApiResponse<TransactionResponseDTO>> deposit(@Valid @RequestBody DepositRequestDTO depositRequestDTO) {
		TransactionResponseDTO response = transactionService.deposit(depositRequestDTO);
		ApiResponse<TransactionResponseDTO> apiResponse = new ApiResponse<>(true, "Money deposit successfully ", response);
		return ResponseEntity.ok(apiResponse);
	}
	
	
	
	@PostMapping("/withdraw")
	public ResponseEntity<ApiResponse<TransactionResponseDTO>> withdraw( @Valid @RequestBody WithdrawRequestDTO withdrawRequestDTO) {
		        TransactionResponseDTO response = transactionService.withdraw(withdrawRequestDTO);
		    	ApiResponse<TransactionResponseDTO> apiResponse = new ApiResponse<>(true, "Money withdraw successfully ", response);
				
		return ResponseEntity.ok(apiResponse);
	}

	

	@PostMapping("/transfer")
	public ResponseEntity<ApiResponse<TransactionResponseDTO>> transfer(@Valid @RequestBody TransferRequestDTO transferRequestDTO) {
		        TransactionResponseDTO response = transactionService.transfer(transferRequestDTO);
		        
		    	ApiResponse<TransactionResponseDTO> apiResponse = new ApiResponse<>(true, "Money Transfer successfully ", response);
				
				return ResponseEntity.ok(apiResponse);

	}

	@GetMapping("getAccountTransaction/{accountNumber}")
	public ResponseEntity<ApiResponse<List<TransactionResponseDTO>>> getAccountHistory(@PathVariable String accountNumber) {
		List<TransactionResponseDTO> response = transactionService.getAccountTransactionHistory(accountNumber);
		ApiResponse<List<TransactionResponseDTO>> apiResponse = new ApiResponse<>(true, "get Account History successfully ", response);	
		return ResponseEntity.ok(apiResponse);
	}

	@GetMapping("getUserTransaction/{id}")
	public ResponseEntity<ApiResponse<List<TransactionResponseDTO>>> getUserAccountHistory(@PathVariable Long id) {
		List<TransactionResponseDTO> response = transactionService.getUserTransactionHistory(id);
		ApiResponse<List<TransactionResponseDTO>> apiResponse = new ApiResponse<>(true, "get User Accounts History successfully ", response);	
		return ResponseEntity.ok(apiResponse);
		
	}

}
