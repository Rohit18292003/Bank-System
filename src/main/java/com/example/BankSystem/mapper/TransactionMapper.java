package com.example.BankSystem.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.BankSystem.dto.TransactionResponseDTO;
import com.example.BankSystem.entity.TransactionEntity;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

	@Mapping(source = "fromAccount.accountNumber", target = "fromAccount")
	@Mapping(source = "toAccount.accountNumber", target = "toAccount")
	@Mapping(source = "fromAccount.balance", target = "availableBalance")
	TransactionResponseDTO toDTO(TransactionEntity transactionEntity);

	List<TransactionResponseDTO> toDTO(List<TransactionEntity> transactionEntities);
}