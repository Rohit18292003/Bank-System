package com.example.BankSystem.exception;

public class AccessDeniedException extends RuntimeException{

	public AccessDeniedException(String msg) {
		super(msg);
	}

}
