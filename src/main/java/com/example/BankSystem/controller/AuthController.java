package com.example.BankSystem.controller;


import com.example.BankSystem.dto.LoginRequest;
import com.example.BankSystem.dto.LoginResponse;
import com.example.BankSystem.security.CustomUserDetails;
import com.example.BankSystem.security.CustomUserDetailsService;
import com.example.BankSystem.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){

        log.info("Enter auth controller");
        //Authenticate User
        Authentication authentication = authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(loginRequest.getEmail() , loginRequest.getPassword()));

        //Load user detail
          CustomUserDetails userdetail = (CustomUserDetails) authentication.getPrincipal();
       // UserDetails userdetail = customUserDetailsService.loadUserByUsername(loginRequest.getEmail());

        //Generate JWT
          String token = jwtService.generateToken(userdetail);
        
        //Return JWT Token
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
