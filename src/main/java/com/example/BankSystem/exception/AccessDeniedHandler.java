package com.example.BankSystem.exception;

public class AccessDeniedHandler extends RuntimeException{

	public AccessDeniedHandler(String msg) {
		super(msg);
	}

}
