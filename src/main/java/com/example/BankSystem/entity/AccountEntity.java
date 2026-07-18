package com.example.BankSystem.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.example.BankSystem.enums.AccountStatus;
import com.example.BankSystem.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Account")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountId;

	@Column(unique = true, nullable = false, length = 12)
	private String accountNumber;

	@Column(name = "balance", nullable = false)
	private BigDecimal balance = BigDecimal.ZERO;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;

	@Enumerated(EnumType.STRING)
	private AccountStatus status;

	// Money sent from this account
	@OneToMany(mappedBy = "fromAccount")
	private List<TransactionEntity> sentTransactions = new ArrayList<>();

	// Money received by this account
	@OneToMany(mappedBy = "toAccount")
	private List<TransactionEntity> receivedTransactions = new ArrayList<>();
}
