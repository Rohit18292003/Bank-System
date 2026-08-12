package com.example.BankSystem.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BankSystem.dto.UserRequestDTO;
import com.example.BankSystem.dto.UserResponseDTO;

import com.example.BankSystem.exception.ApiResponsee;
import com.example.BankSystem.interfaces.UserService;
import com.example.BankSystem.security.CustomUserDetails;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "BearerAuth")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponsee<UserResponseDTO>> createUser(
            @Valid @RequestBody UserRequestDTO users) {
    		
    	UserResponseDTO response = userService.createUser(users);
        ApiResponsee<UserResponseDTO> apiResponse =
                new ApiResponsee<>(true, "User created successfully", response);

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/getUser") 
    public ResponseEntity<ApiResponsee<UserResponseDTO>> getUserData(Authentication authentication) {

    	log.info("get userData method execute inside user controller ");
    	
        ApiResponsee<UserResponseDTO> apiResponse =
                new ApiResponsee<>(true, "User fetched successfully",
                        userService.getUserByEmail(authentication.getName()));

        return ResponseEntity.ok(apiResponse);
    }
    
    @GetMapping("/admin/users/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponsee<UserResponseDTO>> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UserRequestDTO users ,@AuthenticationPrincipal CustomUserDetails userDetails ) {

        UserResponseDTO response = userService.updateUserById(userId, users, userDetails.getUsername());

        ApiResponsee<UserResponseDTO> apiResponse =
                new ApiResponsee<>(true, "User updated successfully", response);

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long userId) {

        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/allUser")
    public ResponseEntity<ApiResponsee<List<UserResponseDTO>>> getAllUsers() {

        ApiResponsee<List<UserResponseDTO>> apiResponse =
                new ApiResponsee<>(true, "All users fetched successfully",
                        userService.getAllUser());

        return ResponseEntity.ok(apiResponse);
    }
}