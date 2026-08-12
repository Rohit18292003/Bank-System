package com.example.BankSystem.dto;

import java.math.BigDecimal;

import com.example.BankSystem.enums.AccountStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request payload for creating or updating an account")
public class AccountRequestDTO {

    @NotNull
    @Schema(
        description = "Unique 12-digit account number",
        example = "123456789012"
    )
    private String accountNumber;

    @Schema(
        description = "Current account status",
        example = "ACTIVE"
    )
    private AccountStatus status;

    @NotNull
    @Schema(
        description = "Initial account balance",
        example = "5000.00"
    )
    private BigDecimal balance;
}