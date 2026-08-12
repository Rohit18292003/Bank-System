package com.example.BankSystem.dto;

import com.example.BankSystem.enums.Role;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response containing user information")
public class UserResponseDTO {

	@Schema(
			description = "Unique ID of the user",
			example = "1"
	)
	private Long id;

	@Schema(
			description = "Full name of the user",
			example = "Rohit Birajdar"
	)
	private String name;

	@Schema(
			description = "Email address of the user",
			example = "rohit@gmail.com"
	)
	private String email;

	@Schema(
			description = "10-digit mobile number",
			example = "9876543210"
	)
	private String mobile;

	@Schema(
			description = "Role assigned to the user",
			example = "USER"
	)
	private Role role;
}