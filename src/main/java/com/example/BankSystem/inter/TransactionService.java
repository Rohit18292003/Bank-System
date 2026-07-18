package com.example.BankSystem.inter;

import java.util.List;
import com.example.BankSystem.dto.DepositRequestDTO;
import com.example.BankSystem.dto.TransactionResponseDTO;
import com.example.BankSystem.dto.TransferRequestDTO;
import com.example.BankSystem.dto.WithdrawRequestDTO;

public interface TransactionService {
	public TransactionResponseDTO deposit(DepositRequestDTO depostRequestDTO);

	public TransactionResponseDTO withdraw(WithdrawRequestDTO withdrawRequestDTO);

	public TransactionResponseDTO transfer(TransferRequestDTO transferRequestDTO);

	public List<TransactionResponseDTO> getAccountTransactionHistory(String accountNum);

	public List<TransactionResponseDTO> getUserTransactionHistory(Long id);

}
