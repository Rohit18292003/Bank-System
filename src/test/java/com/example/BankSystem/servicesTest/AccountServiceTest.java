package com.example.BankSystem.servicesTest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.BankSystem.dto.AccountRequestDTO;
import com.example.BankSystem.dto.AccountResponseDTO;
import com.example.BankSystem.entity.AccountEntity;
import com.example.BankSystem.entity.UserEntity;
import com.example.BankSystem.enums.AccountStatus;
import com.example.BankSystem.enums.Role;
import com.example.BankSystem.mapper.AccountMapper;
import com.example.BankSystem.repos.AccountRepo;
import com.example.BankSystem.repos.UsersRepo;
import com.example.BankSystem.service.AccountServiceImpl;
import com.example.BankSystem.service.UserServiceImp;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

	@Mock
	private UsersRepo userRepo;
	@Mock
	private AccountRepo accountRepo;
	@Mock
	private AccountMapper accountMapper;
	@InjectMocks
	private AccountServiceImpl accountService;

	private UserEntity user1;
	private AccountEntity account1;
	private AccountEntity account2;
	private AccountEntity account3;
	private AccountRequestDTO accountReq;
	private AccountResponseDTO accountResp;
	private AccountResponseDTO accountResp2;

	@BeforeEach
	void setUp() {
		user1 = UserEntity.builder().id(1L).name("Rohit Birajdar").email("birajdarrohit56@gmail.com")
				.mobile("9067792489").active(false).role(Role.CUSTOMER).accounts(new ArrayList<>()).build();

		account1 = AccountEntity.builder().accountId(101L).accountNumber("745536941664").user(user1)
				.status(AccountStatus.ACTIVE).build();
		account2 = AccountEntity.builder().accountId(102L).accountNumber("745536941665").user(user1)
				.status(AccountStatus.ACTIVE).build();

		account3 = AccountEntity.builder().accountId(103L).accountNumber("745536941666").user(user1)
				.status(AccountStatus.ACTIVE).build();
		user1.getAccounts().add(account1);
		user1.getAccounts().add(account2);
		user1.getAccounts().add(account3);

		accountReq = AccountRequestDTO.builder().accountNumber("745536941666").balance(new BigDecimal("1000")).build();

		accountResp = AccountResponseDTO.builder().accountId(101L).accountNumber("745536941664")
				.status(AccountStatus.FROZEN).balance(new BigDecimal("2000")).build();

		accountResp2 = AccountResponseDTO.builder().accountId(102L).accountNumber("745536941665")
				.status(AccountStatus.ACTIVE).balance(new BigDecimal("2000")).build();

	}

	@Test
	void shouldCreateAccount() {

		when(userRepo.findById(1L)).thenReturn(Optional.of(user1));
		// when(user1.getAccounts()).thenReturn(List.of(account1, account2));
		when(accountRepo.existsByAccountNumber(accountReq.getAccountNumber())).thenReturn(false);
		// when(accountReq.getInitialBalance().compareTo(BigDecimal.ZERO)<0).thenReturn(false);
		when(accountMapper.toEntity(accountReq)).thenReturn(account3);

		// act
		ResponseEntity<String> result = accountService.createAccount(accountReq, 1L);

		// result
		Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
		verify(accountRepo).save(account3);
		Assertions.assertEquals("Account created", result.getBody());

	}

	@Test
	void shouldGetAccountByAccountNumber() {
		when(accountRepo.findByAccountNumber("745536941664")).thenReturn(Optional.of(account1));
		when(accountMapper.toDto(account1)).thenReturn(accountResp);

		// act
		ResponseEntity<AccountResponseDTO> result = accountService.getAccountByAccountNumber("745536941664");
		// result
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());

	}

	@Test
	void shouldReturnUsersAllAccounts() {

		when(userRepo.findById(1L)).thenReturn(Optional.of(user1));
		when(accountMapper.toDTO(user1.getAccounts())).thenReturn(List.of(accountResp, accountResp2));

		// act
		ResponseEntity<List<AccountResponseDTO>> status = accountService.getUsersAllAccounts(1L);

		// result
		Assertions.assertEquals(HttpStatus.OK, status.getStatusCode());

	}

	@Test
	void shouldAccountStatusUpdate() {

		when(accountRepo.findByAccountNumber("745536941666")).thenReturn(Optional.of(account3));
		when(accountRepo.save(account3)).thenReturn(account3);

		// act
		ResponseEntity<String> status = accountService.updateUserAccountStatusByNumber("745536941666", accountReq);

		Assertions.assertEquals(HttpStatus.OK, status.getStatusCode());

	}

	@Test
	void shouldDeleteAccount() {
		when(accountRepo.findByAccountNumber("745536941666")).thenReturn(Optional.of(account3));
		// act
		ResponseEntity<String> status = accountService.deleteAccount("745536941666");

		// assertions
		Assertions.assertEquals(HttpStatus.OK, status.getStatusCode());

	}
}
