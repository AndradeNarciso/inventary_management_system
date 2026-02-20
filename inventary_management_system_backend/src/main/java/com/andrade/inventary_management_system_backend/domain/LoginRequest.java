package com.andrade.inventary_management_system_backend.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record LoginRequest(@NotBlank String email, @NotBlank String password) {

}
