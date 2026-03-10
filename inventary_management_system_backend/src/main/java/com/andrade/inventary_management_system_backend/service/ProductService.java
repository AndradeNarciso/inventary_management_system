package com.andrade.inventary_management_system_backend.service;

import org.springframework.web.multipart.MultipartFile;

import com.andrade.inventary_management_system_backend.dto.ProductDto;
import com.andrade.inventary_management_system_backend.dto.Response;

public interface ProductService {

    Response createProduct(String productDto, MultipartFile image);

    Response updateProduct(String productDto, MultipartFile file);

    Response getAll();

    Response getProductById(Long id);

    Response deleteById(Long id);

    Response searchProduct(String value);
}
