package com.example.BankSystem.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.BankSystem.dto.TransactionResponseDTO;
import com.example.BankSystem.entity.AccountEntity;
import com.example.BankSystem.entity.TransactionEntity;

@Repository
public interface TransactionRepo extends JpaRepository<TransactionEntity, Long> {

	Optional<AccountEntity> findByAccountNumber(String accountNum);

	@Query("SELECT t FROM TransactionEntity t WHERE t.fromAccount = :accountNumber OR t.toAccount = :accountNumber")
	List<TransactionEntity> findAllTransactionsForAccount(@Param("accountNumber") String accountNumber);
	


	@Query("SELECT t FROM TransactionEntity t WHERE t.fromAccount IN :accountNumber OR t.toAccount IN :accountNumber")
	List<TransactionEntity> findTransactionUsingAccountNumber(@Param("accountNumber") List<String> accountNumber);
	

}
