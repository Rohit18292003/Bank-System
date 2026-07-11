package com.example.BankSystem.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.BankSystem.entity.AccountEntity;

@Repository
public interface AccountRepo extends JpaRepository< AccountEntity,Long> {

}
