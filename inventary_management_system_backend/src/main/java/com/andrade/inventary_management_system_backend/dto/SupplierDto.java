package com.andrade.inventary_management_system_backend.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Builder
@Data
public class SupplierDto {
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String contactInfo;

    @NotBlank
    private String adress;
}
