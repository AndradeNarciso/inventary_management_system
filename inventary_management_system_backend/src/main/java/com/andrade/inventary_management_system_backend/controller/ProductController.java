package com.andrade.inventary_management_system_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.andrade.inventary_management_system_backend.dto.ProductDto;
import com.andrade.inventary_management_system_backend.dto.Response;
import com.andrade.inventary_management_system_backend.service.implementation.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;
    private final ObjectMapper mapper;

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> createProduct(
            @RequestPart("product") String productDtoStringValue,
            @RequestPart("image") MultipartFile image) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(productDtoStringValue, image));
    }

    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateProduct(
            @RequestPart("product") ProductDto productDto,
            @RequestPart("image") MultipartFile image) {

        return ResponseEntity.ok(productService.updateProduct(productDto, image));
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllProduct() {

        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getProductById(@PathVariable Long id) {

        return ResponseEntity.ok(productService.getProductById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteProductById(@PathVariable Long id) {

        return ResponseEntity.ok(productService.deleteById(id));
    }

    @GetMapping("/search/{value}")
    public ResponseEntity<Response> searchProductByNameOrDescription(@PathVariable("value") String param) {

        return ResponseEntity.ok(productService.searchProduct(param));
    }

}
