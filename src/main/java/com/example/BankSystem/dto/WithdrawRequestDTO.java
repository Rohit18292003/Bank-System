package com.example.BankSystem.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
@Schema(description = "Request payload for withdrawing money from an account")
public class WithdrawRequestDTO {

	@NotBlank
	@Schema(
			description = "Account number from which money will be withdrawn",
			example = "123456789012"
	)
	private String fromAccount;

	@NotNull
	@DecimalMin("0.01")
	@Schema(
			description = "Amount to withdraw. Minimum amount is 0.01",
			example = "500.00",
			minimum = "0.01"
	)
	private BigDecimal amount;
}