package com.andrade.inventary_management_system_backend.dto;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record TransactionRequest(
        @NotBlank Long productId,
        @Min(value = 0) Integer quant,
        @NotBlank UUID supplierId,
        @NotBlank String note,
        @NotBlank String description

) {

}
