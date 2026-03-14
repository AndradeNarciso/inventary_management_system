package com.andrade.inventary_management_system_backend.service;

import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.andrade.inventary_management_system_backend.dto.Response;
import com.andrade.inventary_management_system_backend.dto.TransactionRequest;


public interface TransactionService {
    Response purchase(TransactionRequest transactionRequest);

    Response sell(TransactionRequest transactionRequest);

    Response returnToSupplier(TransactionRequest transactionRequest);

    Response getAllTransaction(String filter, Pageable pega);

    Response getTransactionById(UUID id);

    Response getByMonthAndYear(int month, int year);

    Response updateTransactionStatus(UUID id, String status);

}
