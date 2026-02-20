package com.andrade.inventary_management_system_backend.dto;

import java.util.List;

import com.andrade.inventary_management_system_backend.domain.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Builder
@Data
public class CategoryDto {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private List<Product> product;

}
