package com.andrade.inventary_management_system_backend.controller;

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
import com.andrade.inventary_management_system_backend.dto.CategoryDto;
import com.andrade.inventary_management_system_backend.service.implementation.CategoryServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/categories")

public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @GetMapping("/all")
    public ResponseEntity<Response> getAllCategory() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.getAll());
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateCategory(@PathVariable("id") Long userId,
            @Valid @RequestBody CategoryDto CategoryDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.updateCaterory(userId, CategoryDto));

    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteUserById(@PathVariable("id") Long userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.deleteCategory(userId));

    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> createCategory(@Validated @RequestBody CategoryDto categoryDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.createCategory(categoryDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getCaegoryByID(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.getCategoryById(id));
    }

}
