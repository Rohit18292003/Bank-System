package com.example.BankSystem.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BankSystem.dto.UserRequestDTO;
import com.example.BankSystem.dto.UserResponseDTO;
import com.example.BankSystem.exception.ApiResponse;
import com.example.BankSystem.inter.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/users")
	public ResponseEntity<ApiResponse<UserResponseDTO>> createUser( @Valid @RequestBody UserRequestDTO users) {

		UserResponseDTO response = userService.createUser(users);
		ApiResponse<UserResponseDTO> apiResponse = new ApiResponse<>(true, "User created", response);
		return ResponseEntity.ok(apiResponse);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponseDTO>> getUserData(@PathVariable long id) {
		
		
		ApiResponse<UserResponseDTO> apiResponse = new ApiResponse<>(true, "User Fetch", userService.getUserById(id));
		return ResponseEntity.ok(apiResponse);
	}

	@PostMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(@PathVariable long id,
			 @Valid @RequestBody UserRequestDTO users) {
		
		UserResponseDTO response = userService.updateUserById(id, users);
		
		ApiResponse<UserResponseDTO> apiResponse = new ApiResponse<>(true, "Record Updated", response);
		return ResponseEntity.ok(apiResponse);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable long id) {
		userService.deleteById(id);	
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/allUsers")
	public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers() {
		 
		 ApiResponse<List<UserResponseDTO>> apiResponse = new ApiResponse<>(true, "All User fetched", userService.getAllUser());
		return ResponseEntity.ok(apiResponse);
	}

}
