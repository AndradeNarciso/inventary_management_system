package com.andrade.inventary_management_system_backend.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.andrade.inventary_management_system_backend.enums.Role;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Response(
        int status,
        String message,
        String token,
        Role role,
        String expirationTime,
        Integer totalPage,
        Long totalElement,

        //optionals output
        UserDto user,
        List<UserDto> users,

        SupplierDto supplierDto,
        List<SupplierDto> supplierDtos,

        ProductDto productDto,
        List<ProductDto> productDtos,

        TransactionDto transactionDto,
        List<TransactionDto> transactionDtos,

        CategoryDto categoryDto,
        List<CategoryDto> categoryDtos,

        LocalDateTime timeStamp) {

    public Response {
        if (timeStamp == null) {
            timeStamp = LocalDateTime.now();
        }
    }

}
 