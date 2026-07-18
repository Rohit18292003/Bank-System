package com.example.BankSystem.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.BankSystem.entity.AccountEntity;
import com.example.BankSystem.entity.UserEntity;

@Repository
public interface AccountRepo extends JpaRepository<AccountEntity, Long> {

	boolean existsByAccountNumber(String accountNumber);

	Optional<AccountEntity> findByAccountNumber(String accountNumber);

}
