package com.example.BankSystem.exception;

public class AuthenticationEntryPoint extends RuntimeException{

	public AuthenticationEntryPoint(String msg) {
		super(msg);
	} 

}
