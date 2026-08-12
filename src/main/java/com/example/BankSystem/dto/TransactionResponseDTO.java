package com.example.BankSystem.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.BankSystem.enums.TransactionStatus;
import com.example.BankSystem.enums.TransactionType;

import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(description = "Response containing transaction details")
public class TransactionResponseDTO {

	@Schema(
			description = "Unique transaction ID",
			example = "101"
	)
	private Long id;

	@Schema(
			description = "Account from which money was transferred",
			example = "123456789012"
	)
	private String fromAccount;

	@Schema(
			description = "Account to which money was transferred",
			example = "987654321098"
	)
	private String toAccount;

	@Schema(
			description = "Transaction amount",
			example = "1500.00"
	)
	private BigDecimal amount;

	@Schema(
			description = "Available balance after the transaction",
			example = "8500.00"
	)
	private BigDecimal availableBalance;

	@Schema(
			description = "Type of transaction",
			example = "TRANSFER"
	)
	private TransactionType transactionType;

	@Schema(
			description = "Current status of the transaction",
			example = "SUCCESS"
	)
	private TransactionStatus status;

	@Schema(
			description = "Date and time when the transaction occurred",
			example = "2026-08-12T16:30:00"
	)
	private LocalDateTime transactionDate;

	@Schema(
			description = "Additional description of the transaction",
			example = "Money transferred successfully"
	)
	private String description;
}