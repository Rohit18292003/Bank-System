package com.example.BankSystem.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.BankSystem.enums.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@NotBlank
	private String name;

	@Column(unique = true, nullable = false)
	@Email
	private String email;

	@Column(unique = true, nullable = false)
	@Pattern(regexp = "^[0-9]{10}$")
	private String mobile;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role; // ADMIN, CUSTOMER

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", name=" + name + ", email=" + email + ", mobile=" + mobile + ", role=" + role
				+ ", active=" + active + ", accounts=" + accounts + "]";
	}

	@Column(nullable = false)
	private boolean active;

	@OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = false)
	private List<AccountEntity> accounts = new ArrayList<>();
}
