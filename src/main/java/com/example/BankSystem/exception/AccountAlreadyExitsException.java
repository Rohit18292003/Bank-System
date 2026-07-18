package com.example.BankSystem.exception;

public class AccountAlreadyExitsException extends RuntimeException {

	public AccountAlreadyExitsException(String msg) {
		super(msg);
	}

}
