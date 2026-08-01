package com.example.BankSystem.inter;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.example.BankSystem.dto.UserRequestDTO;
import com.example.BankSystem.dto.UserResponseDTO;
import com.example.BankSystem.entity.UserEntity;

public interface UserService {
	public UserResponseDTO createUser(UserRequestDTO users);

	public UserResponseDTO getUserByEmail(String email);

	public UserResponseDTO updateUserById(Long id, UserRequestDTO user);

	public void deleteById(long id);

	public List<UserResponseDTO> getAllUser();

	public UserResponseDTO getUserById(Long id);
}
