package com.example.BankSystem.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler(InsufficientFundsException.class)
	public ResponseEntity<ErrorResponse> InsufficientFundsException() {
		return null;

	}

	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<Map<String, Object>> AccountNotFoundException(AccountNotFoundException ex) {

		return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Map<String, Object>> UserNotFoundException(UserNotFoundException ex) {

		return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
		return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());

	}

	@ExceptionHandler(AccountAlreadyExitsException.class)
	public ResponseEntity<Map<String, Object>> AccountAlreadyExitsException(AccountAlreadyExitsException ex) {
		return buildResponse(HttpStatus.ALREADY_REPORTED, ex.getMessage());
	}

	@ExceptionHandler(InvalidBalanceException.class)
	public ResponseEntity<Map<String, Object>> InvalidBalanceException(InvalidBalanceException ex) {
		return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	// account
	@ExceptionHandler(AccountStatusException.class)
	public ResponseEntity<Map<String, Object>> AccountStatusException(AccountStatusException ex) {
		return buildResponse(HttpStatus.LOCKED, ex.getMessage());
	}

	private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message) {
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", status.value());
		body.put("message", message);
		return ResponseEntity.status(status).body(body);
	}
}
