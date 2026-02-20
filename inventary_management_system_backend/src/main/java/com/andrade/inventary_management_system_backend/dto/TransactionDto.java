
package com.andrade.inventary_management_system_backend.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import com.andrade.inventary_management_system_backend.domain.Product;
import com.andrade.inventary_management_system_backend.enums.TransactionStatus;
import com.andrade.inventary_management_system_backend.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Builder
@Data
public class TransactionDto {

    private UUID id;

    private Integer totalProduct;

    private BigDecimal totalPrice;

    private TransactionType transactionType;

    private TransactionStatus transactionStatus;

    private String description;
    private String note;

    private Instant createdAt;

    private LocalDateTime updateAt;

    private Product product;

    private UserDto user;

    private SupplierDto supplier;

}
