package com.example.BankSystem.controller;

import org.springframework.http.HttpStatus;
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
import com.example.BankSystem.inter.UserService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@PostMapping("/users")
	public ResponseEntity<?>  createUser(@RequestBody UserRequestDTO users) {
		System.out.println("Contoller invoked");
		return ResponseEntity.status(HttpStatus.OK).body(userService.createUser(users));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDTO> getUserData(@PathVariable long id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}
	
	@PostMapping("/{id}")
	public ResponseEntity<?>  updateUser(@PathVariable long id, @RequestBody UserRequestDTO users) {
		return userService.updateUserById(id ,users);
	}
	
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable long id ){
		return ResponseEntity.ok(userService.deleteById(id));
	}
	
	@GetMapping("/allUsers")
	public ResponseEntity<?> getAllUsers(){
		return ResponseEntity.ok(userService.getAllUser());
	}
	

}
