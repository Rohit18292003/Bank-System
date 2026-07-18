package com.example.BankSystem.servicesTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

import com.example.BankSystem.dto.DepositRequestDTO;
import com.example.BankSystem.dto.TransactionResponseDTO;
import com.example.BankSystem.dto.TransferRequestDTO;
import com.example.BankSystem.dto.WithdrawRequestDTO;
import com.example.BankSystem.entity.AccountEntity;
import com.example.BankSystem.entity.TransactionEntity;
import com.example.BankSystem.entity.UserEntity;
import com.example.BankSystem.enums.AccountStatus;
import com.example.BankSystem.enums.Role;
import com.example.BankSystem.enums.TransactionStatus;
import com.example.BankSystem.enums.TransactionType;
import com.example.BankSystem.mapper.TransactionMapper;
import com.example.BankSystem.repos.AccountRepo;
import com.example.BankSystem.repos.TransactionRepo;
import com.example.BankSystem.repos.UsersRepo;
import com.example.BankSystem.service.TransactionServiceImplement;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

	@Mock
	private TransactionMapper transactionMapper;
	@Mock
	private TransactionRepo transactionRepo;
	@Mock
	private AccountRepo accountRepo;
	@Mock
	private  UsersRepo usersRepo;

	@InjectMocks
	private TransactionServiceImplement transactionService;

	private DepositRequestDTO depositReq;
	private WithdrawRequestDTO withdrawRequestDTO;
	private AccountEntity account;
	private AccountEntity reciverAccount;
	private UserEntity user1;

	private TransactionEntity transactionEntity;
	private TransactionEntity transactionEntity1;
	private TransferRequestDTO transferRequestDTO;
	private TransactionResponseDTO transactionResponseDTO;
	private TransactionResponseDTO transactionResponseDTO1;

	@BeforeEach
	void setUp() {

	    // ---------- User ----------
	    user1 = UserEntity.builder()
	            .id(1L)
	            .name("Rohit Birajdar")
	            .email("birajdarrohit56@gmail.com")
	            .mobile("9067792489")
	            .active(false)
	            .role(Role.CUSTOMER)
	            .accounts(new ArrayList<>())
	            .build();

	    // ---------- Accounts ----------
	    account = AccountEntity.builder()
	            .accountId(101L)
	            .accountNumber("745536941664")
	            .balance(new BigDecimal("5000"))
	            .status(AccountStatus.ACTIVE)
	            .user(user1)
	            .build();

	    reciverAccount = AccountEntity.builder()
	            .accountId(102L)
	            .accountNumber("745536941665")
	            .balance(new BigDecimal("5000"))
	            .status(AccountStatus.ACTIVE)
	            .user(user1)
	            .build();

	    // Maintain both sides of the relationship
	    user1.getAccounts().add(account);
	    user1.getAccounts().add(reciverAccount);

	    // ---------- Request DTOs ----------
	    depositReq = DepositRequestDTO.builder()
	            .amount(new BigDecimal("5000"))
	            .toAccount("745536941664")
	            .build();

	    withdrawRequestDTO = WithdrawRequestDTO.builder()
	            .amount(new BigDecimal("2000"))
	            .fromAccount("745536941664")
	            .build();

	    transferRequestDTO = TransferRequestDTO.builder()
	            .fromAccount("745536941664")
	            .toAccount("745536941665")
	            .amount(new BigDecimal("2000"))
	            .build();

	    // ---------- Transaction Entities ----------
	    transactionEntity = TransactionEntity.builder()
	            .id(1001L)
	            .amount(new BigDecimal("2000"))
	            .transactionType(TransactionType.DEPOSIT)
	            .status(TransactionStatus.SUCCESS)
	            .transactionDate(LocalDateTime.now())
	            .toAccount(account)
	            .build();

	    transactionEntity1 = TransactionEntity.builder()
	            .id(1002L)
	            .amount(new BigDecimal("2000"))
	            .transactionType(TransactionType.DEPOSIT)
	            .status(TransactionStatus.FAILED)
	            .transactionDate(LocalDateTime.now())
	            .toAccount(account)
	            .build();

	    // ---------- Response DTOs ----------
	    transactionResponseDTO = TransactionResponseDTO.builder()
	            .id(1001L)
	            .fromAccount(account.getAccountNumber())
	            .toAccount(reciverAccount.getAccountNumber())
	            .amount(new BigDecimal("2000"))
	            .availableBalance(new BigDecimal("7000"))
	            .transactionType(TransactionType.DEPOSIT)
	            .status(TransactionStatus.SUCCESS)
	            .transactionDate(LocalDateTime.now())
	            .build();

	    transactionResponseDTO1 = TransactionResponseDTO.builder()
	            .id(1002L)
	            .fromAccount(account.getAccountNumber())
	            .toAccount(reciverAccount.getAccountNumber())
	            .amount(new BigDecimal("2000"))
	            .availableBalance(new BigDecimal("7000"))
	            .transactionType(TransactionType.DEPOSIT)
	            .status(TransactionStatus.FAILED)
	            .transactionDate(LocalDateTime.now())
	            .build();
	}

	@Test
	void shouldItDeposit() {

		when(accountRepo.findByAccountNumber("745536941664")).thenReturn(Optional.of(account));
		when(transactionMapper.toDTO(any(TransactionEntity.class))).thenReturn(transactionResponseDTO);
		// act
		TransactionResponseDTO responce = transactionService.deposit(depositReq);
		// result
		Assertions.assertEquals(responce.getTransactionType().DEPOSIT, TransactionType.DEPOSIT);
		Assertions.assertEquals(TransactionStatus.SUCCESS, responce.getStatus().SUCCESS);
	}

	@Test
	void shouldItWithdrawl() {
		when(accountRepo.findByAccountNumber("745536941664")).thenReturn(Optional.of(account));
		when(transactionMapper.toDTO(any(TransactionEntity.class))).thenReturn(transactionResponseDTO);

		//
		TransactionResponseDTO result = transactionService.withdraw(withdrawRequestDTO);

		// result
		Assertions.assertEquals(result.getTransactionType().WITHDRAWAL, TransactionType.WITHDRAWAL);
		Assertions.assertEquals(TransactionStatus.SUCCESS, result.getStatus().SUCCESS);
	}

	@Test
	void shouldItTransfer() {

		when(accountRepo.findByAccountNumber(transferRequestDTO.getFromAccount())).thenReturn(Optional.of(account));
		when(accountRepo.findByAccountNumber(transferRequestDTO.getToAccount()))
				.thenReturn(Optional.of(reciverAccount));
		when(transactionMapper.toDTO(any(TransactionEntity.class))).thenReturn(transactionResponseDTO);

		// act
		TransactionResponseDTO result = transactionService.transfer(transferRequestDTO);

		// result
		Assertions.assertEquals(account.getAccountNumber(), result.getFromAccount());
		Assertions.assertEquals(TransactionStatus.SUCCESS, result.getStatus());

		verify(accountRepo).save(account);
		verify(accountRepo).save(reciverAccount);

	}

	@Test
	void shouldGetAllTransactionHistory() {
		// arrange
		when(accountRepo.findByAccountNumber("745536941664")).thenReturn(Optional.of(account));
		when(transactionRepo.findAllTransactionsForAccount("745536941664"))
				.thenReturn(List.of(transactionEntity, transactionEntity1));
		when(transactionMapper.toDTO(List.of(transactionEntity, transactionEntity1)))
				.thenReturn(List.of(transactionResponseDTO, transactionResponseDTO1));
		// act
		List<TransactionResponseDTO> result = transactionService.getAccountTransactionHistory("745536941664");
		// result

		Assertions.assertEquals(result.size(), 2);

	}
	
	@Test
	void shouldItgetUserTransactionHistory() {
		//arrange
		when(usersRepo.findById(1L)).thenReturn(Optional.of(user1));
		when(transactionRepo.findTransactionUsingAccountNumber(List.of("745536941664","745536941665"))).thenReturn(List.of(transactionEntity, transactionEntity1));
		when(transactionMapper.toDTO(List.of(transactionEntity, transactionEntity1)))
		.thenReturn(List.of(transactionResponseDTO, transactionResponseDTO1));
		//act
		List<TransactionResponseDTO> result = transactionService.getUserTransactionHistory(1L);
		//result

		Assertions.assertEquals(result.size(), 2);
	}
}
