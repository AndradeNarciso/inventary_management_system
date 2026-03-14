package com.andrade.inventary_management_system_backend.dto;

import jakarta.validation.constraints.NotBlank;

public record StatusDto (@NotBlank String status){
    
}
