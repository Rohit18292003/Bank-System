package com.example.BankSystem.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.example.BankSystem.entity.TransactionEntity;

@Repository
public interface TransactionRepo extends JpaRepository<TransactionEntity, Long> {

	

	@Query("SELECT t FROM TransactionEntity t WHERE t.fromAccount.accountNumber = :accountNumber OR t.toAccount.accountNumber = :accountNumber")
	List<TransactionEntity> findAllTransactionsForAccount(@Param("accountNumber") String accountNumber);
	


	@Query("SELECT t FROM TransactionEntity t WHERE t.fromAccount.accountNumber IN :accountNumber OR t.toAccount.accountNumber IN :accountNumber")
	List<TransactionEntity> findTransactionUsingAccountNumber(@Param("accountNumber") List<String> accountNumber);
	

}
