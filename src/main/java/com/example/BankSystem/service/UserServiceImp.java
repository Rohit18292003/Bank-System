package com.example.BankSystem.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.BankSystem.dto.UserRequestDTO;
import com.example.BankSystem.dto.UserResponseDTO;
import com.example.BankSystem.entity.UserEntity;
import com.example.BankSystem.exception.ResourceNotFoundException;
import com.example.BankSystem.inter.UserService;
import com.example.BankSystem.mapper.UsersMapper;
import com.example.BankSystem.repos.UsersRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

	private final UsersRepo userRepo;
	private final UsersMapper userMapper;

	@Override
	@Transactional
	public ResponseEntity createUser(UserRequestDTO users) {

		if (userRepo.existsByEmail(users.getEmail()) || userRepo.existsByMobile(users.getMobile())) {
			return ResponseEntity.status(HttpStatus.FOUND).body("Customer mail or mobile already registered");
		}
		UserEntity customer = userMapper.toEntity(users);
		customer.setActive(true);
		userRepo.save(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body("Customer Created");
	}

	@Override
	public UserResponseDTO getUserById(Long id) {

		UserEntity resp = userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found wit id " + id));
		return userMapper.toDTO(resp);
	}

	@Override
	public ResponseEntity<UserEntity> updateUserById(Long id, UserRequestDTO newUserData) {

		UserEntity customer = userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for update operation with id " + id));

		customer.setName(newUserData.getName());
		customer.setEmail(newUserData.getEmail());
		customer.setMobile(newUserData.getMobile());
		customer.setRole(newUserData.getRole());

		UserEntity updatedUser = userRepo.save(customer);
		return ResponseEntity.ok(updatedUser);
	}

	@Override
	public ResponseEntity<String> deleteById(long id) {
		UserEntity dataForDelete = userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		userRepo.deleteById(dataForDelete.getId());
		return ResponseEntity.ok("User Deleted successfully");
	}

	@Override
	public ResponseEntity<List<UserResponseDTO>> getAllUser() {

		List<UserEntity> allUsersEntity = userRepo.findAll();

		List<UserResponseDTO> allUserDTO = userMapper.toDTO(allUsersEntity);
		return ResponseEntity.ok(allUserDTO);

	}

}
