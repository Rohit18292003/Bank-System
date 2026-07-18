package com.example.BankSystem.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.BankSystem.dto.AccountRequestDTO;
import com.example.BankSystem.dto.AccountResponseDTO;
import com.example.BankSystem.entity.AccountEntity;

import jakarta.validation.Valid;

@Mapper(componentModel = "spring")
public interface AccountMapper {

	AccountResponseDTO toDto(AccountEntity account);

	AccountEntity toEntity(@Valid AccountRequestDTO accountRequestDTO);

	List<AccountResponseDTO> toDTO(List<AccountEntity> accountEntity);

	List<AccountEntity> toEntity(@Valid List<AccountRequestDTO> accountRequestDto);
}