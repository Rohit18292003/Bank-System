package com.example.BankSystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request payload for user login")
public class LoginRequest {

    @Schema(
            description = "Registered email address",
            example = "rohit@gmail.com"
    )
    private String email;

    @Schema(
            description = "User password",
            example = "********"
    )
    private String password;
}