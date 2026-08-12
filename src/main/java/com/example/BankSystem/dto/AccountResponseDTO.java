package com.example.BankSystem.dto;

import java.math.BigDecimal;

import com.example.BankSystem.enums.AccountStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response returned after account operations")
public class AccountResponseDTO {

    @Schema(
        description = "Unique account ID",
        example = "1"
    )
    private Long accountId;

    @Schema(
        description = "Account number",
        example = "123456789012"
    )
    private String accountNumber;

    @Schema(
        description = "Account status",
        example = "ACTIVE"
    )
    private AccountStatus status;

    @Schema(
        description = "Current account balance",
        example = "5000.00"
    )
    private BigDecimal balance;
}