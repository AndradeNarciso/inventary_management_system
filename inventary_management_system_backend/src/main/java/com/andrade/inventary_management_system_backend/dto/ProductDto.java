package com.andrade.inventary_management_system_backend.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Builder
@Data
public class ProductDto {

    private Long id;

    private Long idCategory;
    private UUID idSupplier;
    private Long idProduct;

    @NotBlank
    private String name;

    @NotBlank
    private String sku;

    @Positive
    private BigDecimal price;

    private Integer quantStock;

    private String description;

    private LocalDateTime expireDate;

    private final Instant createdAt = Instant.now();

    private String imageUrl;

}
