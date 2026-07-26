package com.example.BankSystem.inter;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.BankSystem.dto.AccountRequestDTO;
import com.example.BankSystem.dto.AccountResponseDTO;

public interface AccountService {

	public AccountResponseDTO createAccount(AccountRequestDTO account, Long id);

	public AccountResponseDTO getAccountByAccountNumber(String accountNumber);

	public AccountResponseDTO updateAccountStatus(String accountNumber, AccountRequestDTO account);

	public void deleteAccount(String accountNumber);

	public List<AccountResponseDTO> getUsersAllAccounts(Long userID);
	// public ResponseEntity<Map<Object,List<AccountResponseDTO> >>
	// getAllUsersAccounts();

}
