package com.example.BankSystem.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.example.BankSystem.enums.TransactionStatus;
import com.example.BankSystem.enums.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="transaction_history")
public class TransactionEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;


	    @Column(nullable = false)
	    private BigDecimal amount;
	    
	    
	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false)
	    private TransactionType transactionType; // DEPOSIT, WITHDRAWAL, TRANSFER 
	    
	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false)
	    private TransactionStatus status; // SUCCESS, FAILED

	    @CreationTimestamp
	    private LocalDateTime transactionDate;

	    @ManyToOne
	    @JoinColumn(name = "from_account_id")
	    private AccountEntity fromAccount;
	    
	    @ManyToOne
	    @JoinColumn(name = "to_account_id")
		private AccountEntity toAccount;

		private String description;

}
