package com.example.BankSystem.service;

import java.util.List;

import com.example.BankSystem.exception.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.example.BankSystem.dto.DepositRequestDTO;
import com.example.BankSystem.dto.TransactionResponseDTO;
import com.example.BankSystem.dto.TransferRequestDTO;
import com.example.BankSystem.dto.WithdrawRequestDTO;
import com.example.BankSystem.entity.AccountEntity;
import com.example.BankSystem.entity.TransactionEntity;
import com.example.BankSystem.entity.UserEntity;
import com.example.BankSystem.enums.AccountStatus;

import com.example.BankSystem.enums.TransactionStatus;
import com.example.BankSystem.enums.TransactionType;
import com.example.BankSystem.interfaces.TransactionService;
import com.example.BankSystem.mapper.TransactionMapper;
import com.example.BankSystem.repos.AccountRepo;
import com.example.BankSystem.repos.TransactionRepo;
import com.example.BankSystem.repos.UsersRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImplement implements TransactionService {

	private final UsersRepo usersRepo;
	private final TransactionMapper transactionMapper;
	private final TransactionRepo transactionRepo;
	private final AccountRepo accountRepo;

	@Override
	@Transactional
	public TransactionResponseDTO deposit(DepositRequestDTO depostRequestDTO) {

		AccountEntity ifAccountFound = accountRepo.findByAccountNumber(depostRequestDTO.getToAccount())
				.orElseThrow(() -> new AccountNotFoundException("Account not found"));
		if (depostRequestDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
			throw new InvalidBalanceException("Balance can't not be negative");
		}

		if (!ifAccountFound.getStatus().equals(AccountStatus.ACTIVE)) {
			throw new AccountStatusException("Account is not active please contact to bank");
		}

		BigDecimal existsBalance = ifAccountFound.getBalance().add(depostRequestDTO.getAmount());

		ifAccountFound.setBalance(existsBalance);

		TransactionEntity transactionEntity = new TransactionEntity();
		transactionEntity.setAmount(depostRequestDTO.getAmount());
		transactionEntity.setTransactionType(TransactionType.DEPOSIT);
		transactionEntity.setStatus(TransactionStatus.SUCCESS);
		transactionEntity.setTransactionDate(LocalDateTime.now(ZoneId.systemDefault()));
		transactionEntity.setToAccount(ifAccountFound);
		transactionEntity.setDescription("Deposited");

		accountRepo.save(ifAccountFound);

		transactionRepo.save(transactionEntity);
		 
		return transactionMapper.toDTO(transactionEntity);

	}

	@Override
	public TransactionResponseDTO withdraw(WithdrawRequestDTO withdrawRequestDTO , String email) {
		
		
		AccountEntity ifAccountFound = accountRepo.findByAccountNumber(withdrawRequestDTO.getFromAccount())
				.orElseThrow(() -> new AccountNotFoundException("Account not found"));
		
		if(!ifAccountFound.getUser().getEmail().equals(email)) {
			throw new AccessDeniedException("You can't withdrawn money from this account, it required account holder credentials ");
		}
		
		if (withdrawRequestDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
			throw new InvalidBalanceException("Withdraw Balance can't not be negative");
		}	

		if (!ifAccountFound.getStatus().equals(AccountStatus.ACTIVE)) {
			throw new AccountStatusException("Account is not active please contact to bank");
		}

		BigDecimal existsBalance = ifAccountFound.getBalance();
		BigDecimal withdrawAmount = withdrawRequestDTO.getAmount();

		if (ifAccountFound.getBalance().compareTo(withdrawRequestDTO.getAmount()) < 0) {
			throw new InsufficientFundsException("Insufficient Funds");
		}
		BigDecimal totalAfterWithdrawal = existsBalance.subtract(withdrawAmount);
		ifAccountFound.setBalance(totalAfterWithdrawal);

		TransactionEntity transactionEntity = new TransactionEntity();
		transactionEntity.setAmount(withdrawRequestDTO.getAmount());
		transactionEntity.setTransactionType(TransactionType.WITHDRAWAL);
		transactionEntity.setStatus(TransactionStatus.SUCCESS);
		transactionEntity.setTransactionDate(LocalDateTime.now(ZoneId.systemDefault()));
		transactionEntity.setToAccount(ifAccountFound);
		transactionEntity.setDescription("Withdrawl");

		accountRepo.save(ifAccountFound);

		transactionRepo.save(transactionEntity);
		
		return transactionMapper.toDTO(transactionEntity);
	}

	@Override
	public TransactionResponseDTO transfer(TransferRequestDTO transferRequestDTO , String email) {

		AccountEntity fromAccount = accountRepo.findByAccountNumber(transferRequestDTO.getFromAccount())
				.orElseThrow(() -> new AccountNotFoundException("Sender Account not found"));

		AccountEntity toAccount = accountRepo.findByAccountNumber(transferRequestDTO.getToAccount())
				.orElseThrow(() -> new AccountNotFoundException("Reciver Account not found"));
		
		if(!fromAccount.getUser().getEmail().equals(email)) {
			throw new AccessDeniedException("You can't transfer money from this account, it required account holder credentials ");
		}
		
		if (!fromAccount.getStatus().equals(AccountStatus.ACTIVE)) {
			throw new AccountStatusException("Sender account is not active.");
		}
		if (!toAccount.getStatus().equals(AccountStatus.ACTIVE)) {
			throw new AccountStatusException("Receiver account is not active.");
		}


		if (fromAccount.getBalance().compareTo(transferRequestDTO.getAmount()) < 0) {
			throw new InsufficientFundsException("Insufficient Balance can't not be transfer");
		}
		// sender
		BigDecimal availableBalance = fromAccount.getBalance().subtract(transferRequestDTO.getAmount());
		fromAccount.setBalance(availableBalance);
		// reciver
		BigDecimal reciverBalance = toAccount.getBalance().add(transferRequestDTO.getAmount());
		toAccount.setBalance(reciverBalance);

		TransactionEntity transactionEntity = new TransactionEntity();
		transactionEntity.setAmount(transferRequestDTO.getAmount());
		transactionEntity.setTransactionType(TransactionType.TRANSFER);
		transactionEntity.setStatus(TransactionStatus.SUCCESS);
		transactionEntity.setTransactionDate(LocalDateTime.now(ZoneId.systemDefault()));
		transactionEntity.setToAccount(toAccount);
		transactionEntity.setFromAccount(fromAccount);
		transactionEntity.setDescription("Transfer");

		accountRepo.save(fromAccount);
		accountRepo.save(toAccount);

		transactionRepo.save(transactionEntity);
		TransactionResponseDTO res = transactionMapper.toDTO(transactionEntity);
		res.setAvailableBalance(availableBalance);

		return res;
	}

	@Override
	public List<TransactionResponseDTO> getAccountTransactionHistory(String accountNum , Authentication authentication ) {
		
		String role = authentication.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority).orElse("");
	    
		log.info("get Account Transaction History Current login user role is  "+role);
		
		AccountEntity accountDetails = accountRepo.findByAccountNumber(accountNum)
				.orElseThrow(() -> new AccountNotFoundException("Account not found for transaction history"));

		if(accountDetails.getUser().getEmail().equals(authentication.getName()) || role.equals("ROLE_ADMIN")) {
			//record provided
			log.info("Login by customer");
			List<TransactionEntity> history = transactionRepo.findAllTransactionsForAccount(accountNum);
			return transactionMapper.toDTO(history);
		}
		else{
			//throw exception
			throw  new AccessDeniedException("Sorry you cant see transaction history ");
		}
	}

	@Override
	public List<TransactionResponseDTO> getUserTransactionHistory(Long id) {
		UserEntity existstUser = usersRepo.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not found with " + id));

		List<String> userAccount = existstUser.getAccounts().stream().map(AccountEntity::getAccountNumber).toList();

		
		List<TransactionEntity> result = transactionRepo.findTransactionUsingAccountNumber(userAccount);

		return transactionMapper.toDTO(result);
	}

}
