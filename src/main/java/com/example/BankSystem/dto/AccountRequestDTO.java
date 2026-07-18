package com.example.BankSystem.dto;

import java.math.BigDecimal;

import com.example.BankSystem.enums.AccountStatus;
import com.example.BankSystem.enums.Role;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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

public class AccountRequestDTO {

//	@NoArgsConstructor
//	@AllArgsConstructor
	@NotNull
	private String accountNumber;

	private AccountStatus status;
	@NotNull

	private BigDecimal balance;
}
