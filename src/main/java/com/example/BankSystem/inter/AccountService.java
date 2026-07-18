package com.example.BankSystem.inter;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.BankSystem.dto.AccountRequestDTO;
import com.example.BankSystem.dto.AccountResponseDTO;

public interface AccountService {

	public ResponseEntity<String> createAccount(AccountRequestDTO account, Long id);

	public ResponseEntity<AccountResponseDTO> getAccountByAccountNumber(String accountNumber);

	public ResponseEntity<String> updateUserAccountStatusByNumber(String accountNumber, AccountRequestDTO account);

	public ResponseEntity<String> deleteAccount(String accountNumber);

	public ResponseEntity<List<AccountResponseDTO>> getUsersAllAccounts(Long userID);
	// public ResponseEntity<Map<Object,List<AccountResponseDTO> >>
	// getAllUsersAccounts();

}
