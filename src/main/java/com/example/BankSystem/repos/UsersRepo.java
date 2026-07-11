package com.example.BankSystem.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.BankSystem.entity.UserEntity;

@Repository

public interface UsersRepo extends JpaRepository<UserEntity, Long> {

	boolean existsByEmail(String mail);

	boolean existsByMobile(String mobile);

	Optional<UserEntity> findByEmail(String mail);

}
