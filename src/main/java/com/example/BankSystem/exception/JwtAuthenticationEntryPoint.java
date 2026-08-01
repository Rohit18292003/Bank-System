package com.example.BankSystem.exception;

public class JwtAuthenticationEntryPoint extends RuntimeException {


	public JwtAuthenticationEntryPoint(String msg) {
		super(msg);
	}

}
