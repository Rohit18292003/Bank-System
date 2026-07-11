package com.example.BankSystem.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AccountResponseDTO {
	  private Long id;

	    private String accountNumber;

		private boolean active;
		private BigDecimal balance;
}
