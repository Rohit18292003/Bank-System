package com.example.BankSystem.service;

import java.math.BigDecimal;

import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.BankSystem.dto.AccountRequestDTO;
import com.example.BankSystem.dto.AccountResponseDTO;
import com.example.BankSystem.entity.AccountEntity;
import com.example.BankSystem.entity.UserEntity;
import com.example.BankSystem.exception.AccountAlreadyExitsException;
import com.example.BankSystem.exception.AccountNotFoundException;
import com.example.BankSystem.exception.InvalidBalanceException;
import com.example.BankSystem.exception.ResourceNotFoundException;
import com.example.BankSystem.exception.UserNotFoundException;
import com.example.BankSystem.inter.AccountService;
import com.example.BankSystem.mapper.AccountMapper;
import com.example.BankSystem.repos.AccountRepo;
import com.example.BankSystem.repos.UsersRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

	private final AccountRepo accountRepo;
	private final AccountMapper accountMapper;
	private final UsersRepo userRepo;

	@Override
	@Transactional
	public AccountResponseDTO createAccount(AccountRequestDTO account, Long id) {

		log.info("Creating account for user ID: {}", id);

		UserEntity user = userRepo.findById(id).orElseThrow(() -> {
			log.warn("User not found with ID: {}", id);
			return new UserNotFoundException("User not found with ID: " + id);
		});

		if (accountRepo.existsByAccountNumber(account.getAccountNumber())) {
			log.warn("Account number already exists: {}", account.getAccountNumber());
			throw new AccountAlreadyExitsException("Account already exists");
		}

		if (account.getBalance().compareTo(BigDecimal.ZERO) < 0) {
			log.warn("Invalid initial balance: {}", account.getBalance());
			throw new InvalidBalanceException("Balance cannot be negative");
		}

		AccountEntity newAccount = accountMapper.toEntity(account);
		newAccount.setUser(user);

		user.getAccounts().add(newAccount);

		
		AccountEntity response = accountRepo.save(newAccount);

		log.info("Account created successfully. Account Number: {}",response.getAccountNumber());

		return accountMapper.toDto(newAccount);
	}

	@Override
	public AccountResponseDTO getAccountByAccountNumber(String accountNumber) {

		log.info("Fetching account with account number: {}", accountNumber);

		AccountEntity account = accountRepo.findByAccountNumber(accountNumber).orElseThrow(() -> {
			log.warn("Account not found: {}", accountNumber);
			return new AccountNotFoundException("Account not found");
		});

		log.info("Account fetched successfully: {}", accountNumber);

		return accountMapper.toDto(account);
	}

	@Override
	public List<AccountResponseDTO> getUsersAllAccounts(Long userId) {

		log.info("Fetching all accounts for user ID: {}", userId);

		UserEntity user = userRepo.findById(userId).orElseThrow(() -> {
			log.warn("User not found with ID: {}", userId);
			return new ResourceNotFoundException("User not found with ID: " + userId);
		});

		List<AccountEntity> accounts = user.getAccounts();

		log.info("Total accounts found for user {} : {}", userId, accounts.size());

		return accountMapper.toDto(accounts);
	}

	@Override
	@Transactional
	public AccountResponseDTO updateAccountStatus(String accountNumber, AccountRequestDTO account) {

		log.info("Updating account status for account number: {}", accountNumber);

		AccountEntity existingAccount = accountRepo.findByAccountNumber(accountNumber).orElseThrow(() -> {
			log.warn("Account not found: {}", accountNumber);
			return new AccountNotFoundException("Account not found");
		});

		existingAccount.setStatus(account.getStatus());

		AccountEntity updatedAccount = accountRepo.save(existingAccount);

		log.info("Account status updated successfully for account number: {}", accountNumber);

		return accountMapper.toDto(updatedAccount);
	}

	@Override
	@Transactional
	public void deleteAccount(String accountNumber) {

		log.info("Deleting account with account number: {}", accountNumber);

		AccountEntity account = accountRepo.findByAccountNumber(accountNumber).orElseThrow(() -> {
			log.warn("Account not found: {}", accountNumber);
			return new AccountNotFoundException("Account not found");
		});

		accountRepo.delete(account);

		log.info("Account deleted successfully: {}", accountNumber);
	}

}
