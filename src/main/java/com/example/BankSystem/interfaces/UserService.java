package com.example.BankSystem.interfaces;

import java.util.List;

import com.example.BankSystem.dto.UserRequestDTO;
import com.example.BankSystem.dto.UserResponseDTO;

public interface UserService {
	public UserResponseDTO createUser(UserRequestDTO users);

	public UserResponseDTO getUserByEmail(String email);

	public UserResponseDTO updateUserById(Long id, UserRequestDTO user , String mail);

	public void deleteById(long id);

	public List<UserResponseDTO> getAllUser();

	public UserResponseDTO getUserById(Long id);
}
