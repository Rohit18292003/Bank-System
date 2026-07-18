package com.example.BankSystem.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.BankSystem.enums.TransactionStatus;
import com.example.BankSystem.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDTO {
	private Long id;

	private String fromAccount;

	private String toAccount;

	private BigDecimal amount;

	private BigDecimal availableBalance;

	private TransactionType transactionType;

	private TransactionStatus status;

	private LocalDateTime transactionDate;
	private String description;
}
