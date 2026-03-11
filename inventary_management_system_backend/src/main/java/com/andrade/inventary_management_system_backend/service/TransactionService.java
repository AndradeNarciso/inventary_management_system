package com.andrade.inventary_management_system_backend.service;

import java.util.UUID;



import com.andrade.inventary_management_system_backend.dto.Response;
import com.andrade.inventary_management_system_backend.dto.TransactionRequest;
import com.andrade.inventary_management_system_backend.enums.TransactionStatus;

public interface TransactionService {
    Response purchase(TransactionRequest transactionRequest);

    Response sell(TransactionRequest transactionRequest);

    Response returnToSupplier(TransactionRequest transactionRequest);

    Response getAllTransaction(int page, int size, String filter);

    Response getTransactionById(UUID id);

    Response getByMonthAndYear(int month, int year);

    Response UpdateTransactionStatus(UUID id, TransactionStatus status);

}
