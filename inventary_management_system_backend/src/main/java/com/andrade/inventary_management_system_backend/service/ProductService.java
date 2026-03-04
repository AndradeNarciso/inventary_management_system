package com.andrade.inventary_management_system_backend.service;

import org.springframework.web.multipart.MultipartFile;

import com.andrade.inventary_management_system_backend.dto.ProductDto;
import com.andrade.inventary_management_system_backend.dto.Response;

public interface ProductService {

    Response createProduct(ProductDto productDto, MultipartFile image);

    Response updateProduct(Long id, ProductDto productDto);

    Response getAll();

    Response getProductById(Long id);

    Response delteById(Long id);

    Response searchProduct(String value);
}
