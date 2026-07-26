package com.example.BankSystem.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.BankSystem.dto.UserRequestDTO;
import com.example.BankSystem.dto.UserResponseDTO;
import com.example.BankSystem.entity.UserEntity;
import com.example.BankSystem.exception.AccountAlreadyExitsException;
import com.example.BankSystem.exception.ResourceNotFoundException;
import com.example.BankSystem.inter.UserService;
import com.example.BankSystem.mapper.UsersMapper;
import com.example.BankSystem.repos.UsersRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImp implements UserService {

	private final UsersRepo userRepo;
	private final UsersMapper userMapper;

	@Override
	@Transactional
	public UserResponseDTO createUser(UserRequestDTO users) {

	    log.info("Creating user with email: {}", users.getEmail());

	    if (userRepo.existsByEmail(users.getEmail())) {
	        log.warn("User creation failed. Email already exists: {}", users.getEmail());
	        throw new AccountAlreadyExitsException("Email already exists");
	    }

	    if (userRepo.existsByMobile(users.getMobile())) {
	        log.warn("User creation failed. Mobile already exists: {}", users.getMobile());
	        throw new AccountAlreadyExitsException("Mobile number already exists");
	    }

	    UserEntity customer = userMapper.toEntity(users);
	    customer.setActive(true);

	    customer = userRepo.save(customer);

	    log.info("User created successfully with ID: {}", customer.getId());

	    return userMapper.toDTO(customer);
	}

	@Override
	public UserResponseDTO getUserById(Long id) {

	    log.info("Fetching user with ID: {}", id);

	    UserEntity user = userRepo.findById(id)
	            .orElseThrow(() -> {
	                log.warn("User not found with ID: {}", id);
	                return new ResourceNotFoundException("User not found with ID: " + id);
	            });

	    log.info("User fetched successfully with ID: {}", id);

	    return userMapper.toDTO(user);
	}

	@Override
	@Transactional
	public UserResponseDTO updateUserById(Long id, UserRequestDTO newUserData) {

	    log.info("Updating user with ID: {}", id);

	    UserEntity customer = userRepo.findById(id)
	            .orElseThrow(() -> {
	                log.warn("User not found for update with ID: {}", id);
	                return new ResourceNotFoundException("User not found with ID: " + id);
	            });

	    customer.setName(newUserData.getName());
	    customer.setEmail(newUserData.getEmail());
	    customer.setMobile(newUserData.getMobile());
	    customer.setRole(newUserData.getRole());

	    UserEntity updatedUser = userRepo.save(customer);

	    log.info("User updated successfully with ID: {}", updatedUser.getId());

	    return userMapper.toDTO(updatedUser);
	}

	@Override
	@Transactional
	public void deleteById(long id) {

	    log.info("Deleting user with ID: {}", id);

	    UserEntity user = userRepo.findById(id)
	            .orElseThrow(() -> {
	                log.warn("User not found with ID: {}", id);
	                return new ResourceNotFoundException("User not found with ID: " + id);
	            });

	    userRepo.delete(user);

	    log.info("User deleted successfully with ID: {}", id);
	}
	@Override
	public List<UserResponseDTO> getAllUser() {

	    log.info("Fetching all users");

	    List<UserEntity> users = userRepo.findAll();

	    log.info("Total users found: {}", users.size());

	    return userMapper.toDTO(users);
	}

}
