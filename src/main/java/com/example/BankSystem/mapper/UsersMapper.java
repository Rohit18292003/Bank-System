package com.example.BankSystem.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.BankSystem.dto.UserRequestDTO;
import com.example.BankSystem.dto.UserResponseDTO;
import com.example.BankSystem.entity.UserEntity;

import jakarta.validation.Valid;

@Mapper(componentModel = "spring")
public interface UsersMapper {

	// get request from user
	UserEntity toEntity(@Valid UserRequestDTO userReq);

	List<UserEntity> toEntity(@Valid List<UserRequestDTO> userReq);

	UserResponseDTO toDTO(UserEntity resp);

	List<UserResponseDTO> toDTO(List<UserEntity> userReq);

}
