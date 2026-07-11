package com.example.BankSystem.dto;


import com.example.BankSystem.enums.Role;

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
public class UserRequestDTO {

	   @NotBlank
	    private String name;

	    @Email
	    private String email;

	    @Pattern(regexp = "^[0-9]{10}$")
	    private String mobile;
	    
	  
	    
	    @NotNull
	    private Role role;
	    // getters setters

		
}
