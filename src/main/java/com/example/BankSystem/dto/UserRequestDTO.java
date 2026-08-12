package com.example.BankSystem.dto;

import com.example.BankSystem.enums.Role;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request payload for creating or updating a user")
public class UserRequestDTO {

	@NotBlank
	@Schema(
			description = "Full name of the user",
			example = "Rohit Birajdar"
	)
	private String name;

	@Email
	@Schema(
			description = "Valid email address of the user",
			example = "rohit@gmail.com"
	)
	private String email;

	@Column
	@Schema(
			description = "Password used for user authentication",
			example = "Rohit@123"
	)
	private String password;

	@Pattern(regexp = "^[0-9]{10}$")
	@Schema(
			description = "10-digit mobile number",
			example = "9876543210"
	)
	private String mobile;

	@NotNull
	@Schema(
			description = "Role assigned to the user",
			example = "USER"
	)
	private Role role;
}