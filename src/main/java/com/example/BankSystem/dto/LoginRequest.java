package com.example.BankSystem.dto;


import lombok.Builder;
import lombok.Data;

@Data
public class LoginRequest {
    String email;
    String password;
}
