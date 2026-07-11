package com.example.BankSystem.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.BankSystem.entity.TransactionEntity;

@Repository
public interface TransactionRepo extends JpaRepository<TransactionEntity, Long> {

}
