package com.example.BankSystem.exception;

public class InvalidBalanceException extends RuntimeException {

	public InvalidBalanceException(String msg) {
		super(msg);
	}

}
