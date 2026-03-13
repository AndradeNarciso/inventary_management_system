package com.andrade.inventary_management_system_backend.dto;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record TransactionRequest(
                @NotNull Long productId,
                @Min(value = 0) Integer quant,
                UUID supplierId,
                @NotBlank String note,
                @NotBlank String description

) {

}
