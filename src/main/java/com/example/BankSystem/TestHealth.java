package com.example.BankSystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestHealth {
    @Bean
    public CommandLineRunner run(){
        return args -> System.out.println("Hello from Test");
    }
}
