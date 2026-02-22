package com.andrade.inventary_management_system_backend.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.andrade.inventary_management_system_backend.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;


import lombok.Builder;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Builder
@Data
public class UserDto {
    private UUID id;

    private String name;

    private String email;

    @JsonIgnore
    private String password;

    private Role role;

    private String phoneNumber;

    private  Instant createdAt;

    private List<TransactionDto> transactions;

}
