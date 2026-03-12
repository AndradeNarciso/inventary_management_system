package com.andrade.inventary_management_system_backend.controller;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.andrade.inventary_management_system_backend.dto.Response;
import com.andrade.inventary_management_system_backend.dto.TransactionRequest;
import com.andrade.inventary_management_system_backend.enums.TransactionStatus;
import com.andrade.inventary_management_system_backend.service.TransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/purchase")
    public ResponseEntity<Response> purchase(@Valid @RequestBody TransactionRequest transactionRequest) {
        return ResponseEntity.ok(transactionService.purchase(transactionRequest));
    }

    @PostMapping("/sell")
    public ResponseEntity<Response> sell(@Valid @RequestBody TransactionRequest transactionRequest) {
        return ResponseEntity.ok(transactionService.sell(transactionRequest));
    }

    @PostMapping("/return-to-supplier")
    public ResponseEntity<Response> returnToSupllier(@Valid @RequestBody TransactionRequest transactionRequest) {
        return ResponseEntity.ok(transactionService.returnToSupplier(transactionRequest));
    }

    @GetMapping("/all/{filter}")
    public ResponseEntity<Response> getAllTransaction(@PathVariable String filter, Pageable page) {
        return ResponseEntity.ok(transactionService.getAllTransaction(filter, page));
    }

    @GetMapping("/all/data")
    public ResponseEntity<Response> getAllTransactionByMontAndYear(@RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {

        Integer monthParam = (month != null) ? month : LocalDate.now().getMonthValue(); // if user don't input, it take
                                                                                        // the current data
        Integer yearParam = (year != null) ? year : LocalDate.now().getYear();

        return ResponseEntity.ok(transactionService.getByMonthAndYear(monthParam, yearParam));
    }

    @GetMapping("/{id}}")
    public ResponseEntity<Response> getAllTransactionById(@PathVariable UUID id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @PutMapping("/update/{id}")

    public ResponseEntity<Response> UpdateTransaction(@PathVariable UUID id,
            @Valid @RequestBody TransactionStatus status) {
        return ResponseEntity.ok(transactionService.UpdateTransactionStatus(id, status));
    }
}
