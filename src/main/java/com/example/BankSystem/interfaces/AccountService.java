package com.example.BankSystem.interfaces;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.example.BankSystem.dto.AccountRequestDTO;
import com.example.BankSystem.dto.AccountResponseDTO;

public interface AccountService {

	public AccountResponseDTO createAccount(AccountRequestDTO account, Long id, Authentication authentication);

	public AccountResponseDTO getAccountByAccountNumber(String accountNumber, String accountEmail);

	public AccountResponseDTO updateAccountStatus(String accountNumber, AccountRequestDTO account, String mail );

	public void deleteAccount(String accountNumber , String mail);

	public List<AccountResponseDTO> getUsersAllAccounts(Long userID);
	// public ResponseEntity<Map<Object,List<AccountResponseDTO> >>
	// getAllUsersAccounts();

	
}
