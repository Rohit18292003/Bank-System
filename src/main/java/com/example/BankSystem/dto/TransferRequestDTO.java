package com.example.BankSystem.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferRequestDTO {
	@NotBlank
    private String fromAccount;
	@NotBlank
    private String toAccount;

	@NotNull
	@DecimalMin("0.01")
    private BigDecimal amount;
}
