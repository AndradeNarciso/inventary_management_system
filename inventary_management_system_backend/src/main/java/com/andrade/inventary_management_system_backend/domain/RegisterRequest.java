package com.andrade.inventary_management_system_backend.domain;

import com.andrade.inventary_management_system_backend.enums.Role;


import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank(message = "Name is required") String name,
        @NotBlank(message = "Email is required") String email,
        @NotBlank(message = "Password is required") String password,
        @NotBlank Role role) {

}
