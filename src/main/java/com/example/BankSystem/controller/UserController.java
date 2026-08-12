package com.example.BankSystem.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
@Tag(
        name = "User API",
        description = "APIs for managing users"
)
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Create User",
            description = "Creates a new user in the banking system."
    )
    @PostMapping
    public ResponseEntity<ApiResponsee<UserResponseDTO>> createUser(
            @Valid @RequestBody UserRequestDTO users) {

        UserResponseDTO response = userService.createUser(users);

        ApiResponsee<UserResponseDTO> apiResponse =
                new ApiResponsee<>(
                        true,
                        "User created successfully",
                        response
                );

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(
            summary = "Get Current User",
            description = "Fetches the details of the currently authenticated user using the JWT username/email."
    )
    @GetMapping("/getUser")
    public ResponseEntity<ApiResponsee<UserResponseDTO>> getUserData(
            Authentication authentication) {

        log.info("get userData method execute inside user controller");

        ApiResponsee<UserResponseDTO> apiResponse =
                new ApiResponsee<>(
                        true,
                        "User fetched successfully",
                        userService.getUserByEmail(authentication.getName())
                );

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(
            summary = "Get User By ID",
            description = "Fetches a user using their unique user ID."
    )
    @GetMapping("/admin/users/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(

            @Parameter(
                    description = "Unique ID of the user",
                    example = "1"
            )
            @PathVariable Long id) {

        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(
            summary = "Update User",
            description = "Updates the authenticated user's information."
    )
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponsee<UserResponseDTO>> updateUser(

            @Parameter(
                    description = "ID of the user to update",
                    example = "1"
            )
            @PathVariable Long userId,

            @Valid @RequestBody UserRequestDTO users,

            @AuthenticationPrincipal CustomUserDetails userDetails) {

        UserResponseDTO response =
                userService.updateUserById(
                        userId,
                        users,
                        userDetails.getUsername()
                );

        ApiResponsee<UserResponseDTO> apiResponse =
                new ApiResponsee<>(
                        true,
                        "User updated successfully",
                        response
                );

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(
            summary = "Delete User",
            description = "Deletes a user using their unique user ID."
    )
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(

            @Parameter(
                    description = "ID of the user to delete",
                    example = "1"
            )
            @PathVariable Long userId) {

        userService.deleteById(userId);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get All Users",
            description = "Fetches all users from the banking system."
    )
    @GetMapping("/allUser")
    public ResponseEntity<ApiResponsee<List<UserResponseDTO>>> getAllUsers() {

        ApiResponsee<List<UserResponseDTO>> apiResponse =
                new ApiResponsee<>(
                        true,
                        "All users fetched successfully",
                        userService.getAllUser()
                );

        return ResponseEntity.ok(apiResponse);
    }
}