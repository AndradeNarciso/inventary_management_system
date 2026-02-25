package com.andrade.inventary_management_system_backend.service;

import com.andrade.inventary_management_system_backend.dto.CategoryDto;
import com.andrade.inventary_management_system_backend.dto.Response;

public interface CategoryService {

    Response createCategory(CategoryDto categoryDto);

    Response getAll();

    Response getCategoryById(Long id);

    Response updateCaterory(Long id, CategoryDto categoryDto);

    Response deleteCategory(long id);
}
