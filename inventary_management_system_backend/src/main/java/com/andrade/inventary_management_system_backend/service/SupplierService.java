package com.andrade.inventary_management_system_backend.service;

import com.andrade.inventary_management_system_backend.dto.SupplierDto;


import java.util.UUID;

import com.andrade.inventary_management_system_backend.dto.Response;

public interface SupplierService {

    Response createSupplier(SupplierDto suppliCategoryDto);

    Response getAll();

    Response getSupplierById(UUID id);

    Response updateSupplier(UUID id, SupplierDto supplierDto);

    Response deleteSupplier(UUID id);

    
}
