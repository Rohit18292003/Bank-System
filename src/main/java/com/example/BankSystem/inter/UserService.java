package com.example.BankSystem.inter;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.BankSystem.dto.UserRequestDTO;
import com.example.BankSystem.dto.UserResponseDTO;
import com.example.BankSystem.entity.UserEntity;

public interface UserService {
	public UserResponseDTO createUser(UserRequestDTO users);

	public UserResponseDTO getUserById(Long id);

	public UserResponseDTO updateUserById(Long id, UserRequestDTO user);

	public void deleteById(long id);

	public List<UserResponseDTO> getAllUser();
}
