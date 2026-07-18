package com.example.BankSystem.service;

import java.math.BigDecimal;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

	private final AccountRepo accountRepo;
	private final AccountMapper accountMapper;
	private final UsersRepo userRepo;

	@Override
	@Transactional
	public ResponseEntity<String> createAccount(AccountRequestDTO account, Long id) {
		UserEntity userDetail = userRepo.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User Not found " + id));

		if (accountRepo.existsByAccountNumber(account.getAccountNumber())) {
			throw new AccountAlreadyExitsException("Account already exits");
		}

		if (account.getBalance().compareTo(BigDecimal.ZERO) < 0) {
			throw new InvalidBalanceException("Balance can't not be negative");
		}

		AccountEntity newAccount = accountMapper.toEntity(account);

		newAccount.setUser(userDetail);
		userDetail.getAccounts().add(newAccount);
		accountRepo.save(newAccount);

		return ResponseEntity.status(HttpStatus.CREATED).body("Account created");
	}

	@Override
	public ResponseEntity<AccountResponseDTO> getAccountByAccountNumber(String accountNumber) {
		AccountEntity existsAccount = accountRepo.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new AccountNotFoundException("Account not found "));
		AccountResponseDTO responseExistsAccount = accountMapper.toDto(existsAccount);
		return ResponseEntity.ok(responseExistsAccount);
	}

	@Override
	public ResponseEntity<List<AccountResponseDTO>> getUsersAllAccounts(Long userID) {
		UserEntity user = userRepo.findById(userID)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userID));
		List<AccountEntity> accounts = user.getAccounts();

		List<AccountResponseDTO> accountResponses = accountMapper.toDTO(accounts);

		return ResponseEntity.ok(accountResponses);
	}

	@Override
	public ResponseEntity<String> updateUserAccountStatusByNumber(String accountNumber, AccountRequestDTO account) {
		AccountEntity existsAccount = accountRepo.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new AccountNotFoundException("Account not found "));

		existsAccount.setStatus(account.getStatus());
		accountRepo.save(existsAccount);

		return ResponseEntity.ok("Status Updated");
	}

	@Override
	public ResponseEntity<String> deleteAccount(String accountNumber) {
		AccountEntity existsAccount = accountRepo.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new AccountNotFoundException("Account not found "));
		accountRepo.delete(existsAccount);
		return ResponseEntity.ok("Account deleted");
	}

}
