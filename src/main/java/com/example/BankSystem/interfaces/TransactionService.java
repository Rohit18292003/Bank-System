package com.example.BankSystem.interfaces;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.example.BankSystem.dto.DepositRequestDTO;
import com.example.BankSystem.dto.TransactionResponseDTO;
import com.example.BankSystem.dto.TransferRequestDTO;
import com.example.BankSystem.dto.WithdrawRequestDTO;

public interface TransactionService {
	public TransactionResponseDTO deposit(DepositRequestDTO depostRequestDTO);

	public TransactionResponseDTO withdraw(WithdrawRequestDTO withdrawRequestDTO, String email);

	public TransactionResponseDTO transfer(TransferRequestDTO transferRequestDTO, String mail );

	public List<TransactionResponseDTO> getAccountTransactionHistory(String accountNum, Authentication authentication);

	public List<TransactionResponseDTO> getUserTransactionHistory(Long id);

}
