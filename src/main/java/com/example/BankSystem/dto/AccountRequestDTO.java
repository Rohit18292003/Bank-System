package com.example.BankSystem.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AccountRequestDTO {

	@NotNull
    private String accountNumber;
	@NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal initialBalance;
}
