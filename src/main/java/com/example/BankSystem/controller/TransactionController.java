package com.example.BankSystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BankSystem.dto.DepositRequestDTO;
import com.example.BankSystem.dto.TransferRequestDTO;
import com.example.BankSystem.dto.WithdrawRequestDTO;
import com.example.BankSystem.inter.TransactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {

	private final TransactionService transactionService;

	@PostMapping("/withdraw")
	public ResponseEntity<String> withdraw(@RequestBody WithdrawRequestDTO withdrawRequestDTO) {
		transactionService.withdraw(withdrawRequestDTO);
		return ResponseEntity.ok("Money withdraw successfully");
	}

	@PostMapping("/deposit")
	public ResponseEntity<String> deposit(@RequestBody DepositRequestDTO depositRequestDTO) {
		return ResponseEntity.ok("Money deposit successfully");
	}

	@PostMapping("/transfer")
	public ResponseEntity<String> transfer(@RequestBody TransferRequestDTO transferRequestDTO) {
		return ResponseEntity.ok("Money Transfer successfully");
	}

	@GetMapping("getAccountTransaction/{accountNum}")
	public ResponseEntity<String> getAccountHistory(@PathVariable String accountNum) {
		return ResponseEntity.ok("get Account History successfully");
	}

	@GetMapping("getUserTransaction/{id}")
	public ResponseEntity<String> getUserAccountHistory(@PathVariable Long id) {
		return ResponseEntity.ok("get User Accounts History successfully");
	}

}
