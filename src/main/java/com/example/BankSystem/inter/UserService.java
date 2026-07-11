package com.example.BankSystem.inter;



import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.BankSystem.dto.UserRequestDTO;
import com.example.BankSystem.dto.UserResponseDTO;
import com.example.BankSystem.entity.UserEntity;

public interface UserService {
	public ResponseEntity<UserResponseDTO>  createUser(UserRequestDTO users);
	public UserResponseDTO getUserById(Long id);
	public ResponseEntity<UserEntity> updateUserById(Long id, UserRequestDTO user);
	public  ResponseEntity<?> deleteById(long id);
	public ResponseEntity<?> getAllUser();
}
