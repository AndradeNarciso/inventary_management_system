package com.andrade.inventary_management_system_backend.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andrade.inventary_management_system_backend.dto.Response;
import com.andrade.inventary_management_system_backend.dto.SupplierDto;
import com.andrade.inventary_management_system_backend.service.implementation.SupplierServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/suplliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierServiceImpl supplierService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllSupplier() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(supplierService.getAll());
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateCategory(@PathVariable("id") UUID userId,
            @Valid @RequestBody SupplierDto supplierDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(supplierService.updateSupplier(userId, supplierDto));

    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteUserById(@PathVariable("id") UUID userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(supplierService.deleteSupplier(userId));

    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> createCategory(@Validated @RequestBody SupplierDto supplierDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(supplierService.createSupplier(supplierDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getCaegoryByID(@PathVariable UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(supplierService.getSupplierById(id));
    }

}
