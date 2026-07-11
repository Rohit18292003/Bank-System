package com.example.BankSystem.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.BankSystem.dto.AccountRequestDTO;
import com.example.BankSystem.dto.AccountResponseDTO;
import com.example.BankSystem.entity.AccountEntity;
import com.example.BankSystem.entity.UserEntity;
import com.example.BankSystem.exception.UserNotFoundException;
import com.example.BankSystem.inter.AccountService;
import com.example.BankSystem.mapper.AccountMapper;

import com.example.BankSystem.repos.AccountRepo;
import com.example.BankSystem.repos.UsersRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

	private final AccountRepo accountRepo;

	private final AccountMapper accountMapper;
	private final UsersRepo userRepo;

	@Override
	public ResponseEntity<String> createAccount(AccountRequestDTO account, Long id) {

		UserEntity userDetail = userRepo.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User Not found " + id));
		List<AccountEntity> userAccounts = userDetail.getAccounts();
	

		return null;
	}

	@Override
	public ResponseEntity<AccountResponseDTO> getAccountById(Long accountId) {

		return null;
	}

	@Override
	public ResponseEntity<AccountResponseDTO> getAccountByAccountNumber(String accountNumber) {

		return null;
	}

	@Override
	public ResponseEntity<String> updateAccount(Long accountId, AccountRequestDTO account) {

		return null;
	}

	@Override
	public ResponseEntity<String> deleteAccount(Long accountId) {

		return null;
	}

	@Override
	public ResponseEntity<List<AccountResponseDTO>> getUsersAllAccounts() {

		return null;
	}

}
