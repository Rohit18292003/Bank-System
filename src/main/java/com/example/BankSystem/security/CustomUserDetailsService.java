package com.example.BankSystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.BankSystem.entity.UserEntity;
import com.example.BankSystem.exception.UserNotFoundException;
import com.example.BankSystem.repos.UsersRepo;

import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService{


	private final UsersRepo userRepo;
	
	public CustomUserDetailsService(UsersRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		log.info("load User By Username for");
		UserEntity user = userRepo.findByEmail(email).orElseThrow(()-> new UserNotFoundException("User not found "));
		log.info("Mail of user "+user.getEmail());
	return new CustomUserDetails(user);
	}

}
