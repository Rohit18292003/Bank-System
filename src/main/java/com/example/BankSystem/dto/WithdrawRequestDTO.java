package com.example.BankSystem.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WithdrawRequestDTO {
	 private String accountNumber;
	    private BigDecimal amount;
}
