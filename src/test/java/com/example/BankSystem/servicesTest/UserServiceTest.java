package com.example.BankSystem.servicesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.BankSystem.dto.UserRequestDTO;
import com.example.BankSystem.dto.UserResponseDTO;
import com.example.BankSystem.entity.UserEntity;
import com.example.BankSystem.enums.Role;
import com.example.BankSystem.mapper.UsersMapper;
import com.example.BankSystem.repos.UsersRepo;
import com.example.BankSystem.service.UserServiceImp;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UsersRepo userRepo;
	@Mock
	private UsersMapper userMapper;
	@Mock
	private PasswordEncoder passwordEncoder;
	@InjectMocks
	private UserServiceImp userService;
	

	private UserEntity user1;
	private UserEntity user2;
	UserRequestDTO newUserRequest;
	UserResponseDTO respUser1;
	private UserResponseDTO respUser2;

	@BeforeEach
	void setUp() {
		user1 = UserEntity.builder().id(1L).name("Rohit Birajdar").email("birajdarrohit56@gmail.com")
				.mobile("9067792489").active(false).role(Role.CUSTOMER).build();
		user2 = UserEntity.builder().id(2L).name("Jay").email("jay@123").mobile("9067862477").active(true)
				.role(Role.CUSTOMER).build();
		newUserRequest = UserRequestDTO.builder().name("Rohit Birajdar").email("birajdarrohit56@gmail.com").password("rohit123")
				.mobile("9067792489").role(Role.CUSTOMER).build();

		respUser1 = UserResponseDTO.builder().id(1L).name("Rohit Birajdar").email("birajdarrohit56@gmail.com")
				.mobile("9067792489").role(Role.CUSTOMER).build();
		
		
		respUser2 = UserResponseDTO.builder().id(2L).name("Jay").email("jay@123").mobile("9067862477")
				.role(Role.CUSTOMER).build();
	}

	@Test

	void createUser() {

		when(userRepo.existsByEmail(newUserRequest.getEmail())).thenReturn(false);
		when(userRepo.existsByMobile(newUserRequest.getMobile())).thenReturn(false);
		when(userMapper.toEntity(newUserRequest)).thenReturn(user1);
		when(userMapper.toDTO(user1)).thenReturn(respUser1);

		when(passwordEncoder.encode(newUserRequest.getPassword())).thenReturn("rohit123");
		when(userRepo.save(user1)).thenReturn(user1);

		// act

		UserResponseDTO result = userService.createUser(newUserRequest);

		System.out.println(result);
		// result
		// Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());

		verify(userRepo).save(user1);
		// Assertions.assertEquals("Customer Created", result.getBody());
	}

	@Test

	void getUserByIdTest() {
		UserEntity user1 = UserEntity.builder().id(1L).name("Rohit").email("rohit@123").mobile("9067792489")
				.active(false).role(Role.CUSTOMER).build();

		UserResponseDTO result = UserResponseDTO.builder().id(1L).name("Rohit").email("Rohit@123").mobile("9067792489")
				.role(Role.CUSTOMER).build();

		// stubbings
		when(userRepo.findById(1L)).thenReturn(Optional.of(user1));
		when(userMapper.toDTO(user1)).thenReturn(result);

		userService.getUserById(1L);

		assertEquals(user1.getId(), result.getId());

	}

	@Test

	void updateUser() {

		when(userRepo.findById(user1.getId())).thenReturn(Optional.of(user1));

		when(userRepo.save(user1)).thenReturn(user1);
		when(userMapper.toDTO(user1)).thenReturn(respUser1);

		// act
		UserResponseDTO status = userService.updateUserById(1L, newUserRequest,"birajdarrohit56@gmail.com");

		// Assertions.assertEquals(HttpStatus.OK, status.get());

		assertEquals("Rohit Birajdar", status.getName());
		assertEquals("birajdarrohit56@gmail.com", status.getEmail());
		assertEquals("9067792489", status.getMobile());
		assertEquals(Role.CUSTOMER, status.getRole());

		verify(userRepo).findById(1L);
		verify(userRepo).save(user1);

	}

	@Test

	void deleteUser() {

		// arrange
		when(userRepo.findById(user1.getId())).thenReturn(Optional.of(user1));

		// act
		userService.deleteById(user1.getId());

		// assert
		verify(userRepo).findById(user1.getId());
		verify(userRepo).delete(user1);

	}

	@Test
	void getAllUserTest() {

		when(userRepo.findAll()).thenReturn(List.of(user1, user2));
		when(userMapper.toDTO(List.of(user1, user2))).thenReturn(List.of(respUser1, respUser2));
		// act
		 List<UserResponseDTO> resultOfAllUser = userService.getAllUser();

		// assertion

		Assertions.assertNotNull(resultOfAllUser);
		Assertions.assertEquals(2, resultOfAllUser.size());

		verify(userRepo).findAll();
		verify(userMapper).toDTO(List.of(user1, user2));

	}
}
