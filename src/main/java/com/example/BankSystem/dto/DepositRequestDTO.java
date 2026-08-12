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
@Schema(description = "Request payload for depositing money into an account")
public class DepositRequestDTO {

	@NotBlank
	@Schema(
			description = "Account number into which money will be deposited",
			example = "123456789012"
	)
	private String toAccount;

	@NotNull
	@DecimalMin("0.01")
	@Schema(
			description = "Amount to deposit. Minimum amount is 0.01",
			example = "1000.00",
			minimum = "0.01"
	)
	private BigDecimal amount;
}