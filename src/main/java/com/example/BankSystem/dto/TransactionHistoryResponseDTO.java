package com.example.BankSystem.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.BankSystem.enums.TransactionStatus;
import com.example.BankSystem.enums.TransactionType;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TransactionHistoryResponseDTO {
	private Long id;

    private String fromAccount;

    private String toAccount;

    private BigDecimal amount;

    private TransactionType  transactionType;

    private TransactionStatus  status;

    private LocalDateTime transactionDate;
}
