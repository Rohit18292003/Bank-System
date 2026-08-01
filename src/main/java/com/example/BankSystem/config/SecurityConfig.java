package com.example.BankSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

	@Bean
	public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable())
		.httpBasic(Customizer.withDefaults())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(
						        "/swagger-ui/**",
						        "/v3/api-docs/**"
						).permitAll()
						// public
						.requestMatchers("/health").permitAll()
						
						// register user
						.requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
						// user apis
						.requestMatchers(HttpMethod.GET, "/api/v1/users/getUser").hasAnyRole("CUSTOMER", "ADMIN")
						.requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasAnyRole("CUSTOMER", "ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasAnyRole("ADMIN")

						// account api
						.requestMatchers(HttpMethod.POST, "/api/v1/users/*/accounts").hasRole("CUSTOMER")
						.requestMatchers(HttpMethod.GET, "/api/v1/accounts/**").hasAnyRole("CUSTOMER", "ADMIN")
						.requestMatchers(HttpMethod.PUT, "/api/v1/accounts/**").hasAnyRole("CUSTOMER", "ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/api/v1/accounts/**").hasAnyRole("CUSTOMER", "ADMIN")

						// ADMIN API
						.requestMatchers(HttpMethod.GET, "/api/v1/users/*/accounts").hasRole("ADMIN")
						.requestMatchers("/api/v1/users/admin/**").hasRole("ADMIN")

						// transaction apis

						.requestMatchers(HttpMethod.POST, "/api/v1/transactions/**").hasRole("CUSTOMER")
						.requestMatchers(HttpMethod.GET, "/api/v1/users/*/transactions").hasAnyRole("CUSTOMER", "ADMIN")
						.requestMatchers(HttpMethod.GET, "/api/v1/accounts/*/transactions").hasAnyRole("CUSTOMER", "ADMIN")

						.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public AuthenticationManager authenticationManager(
	        AuthenticationConfiguration configuration)
	        throws Exception {

	    return configuration.getAuthenticationManager();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider(
	        UserDetailsService userDetailsService,
	        PasswordEncoder passwordEncoder) {

	    DaoAuthenticationProvider provider =
	            new DaoAuthenticationProvider(userDetailsService);

	
	    provider.setPasswordEncoder(passwordEncoder);

	    return provider;
	}
	
	
}
