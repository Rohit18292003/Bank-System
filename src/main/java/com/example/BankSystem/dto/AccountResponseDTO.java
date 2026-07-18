package com.example.BankSystem.dto;

import java.math.BigDecimal;

import com.example.BankSystem.enums.AccountStatus;
import com.example.BankSystem.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponseDTO {
	private Long accountId;

	private String accountNumber;

	private AccountStatus status;
	private BigDecimal balance;
}
