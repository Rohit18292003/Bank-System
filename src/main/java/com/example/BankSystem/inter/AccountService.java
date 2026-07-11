package com.example.BankSystem.inter;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.BankSystem.dto.AccountRequestDTO;
import com.example.BankSystem.dto.AccountResponseDTO;

public interface AccountService {

	public ResponseEntity<String> createAccount(AccountRequestDTO account, Long id);
	public ResponseEntity<AccountResponseDTO> getAccountById(Long accountId);
	public ResponseEntity<AccountResponseDTO> getAccountByAccountNumber(String accountNumber);
	public ResponseEntity<String> updateAccount(Long accountId ,AccountRequestDTO account);
	public ResponseEntity<String> deleteAccount(Long accountId);
	public ResponseEntity<List<AccountResponseDTO>> getUsersAllAccounts();
	//public ResponseEntity<Map<Object,List<AccountResponseDTO> >> getAllUsersAccounts();
	
}
