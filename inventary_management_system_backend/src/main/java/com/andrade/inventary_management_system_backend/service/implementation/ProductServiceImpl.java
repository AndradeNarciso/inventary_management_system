package com.andrade.inventary_management_system_backend.service.implementation;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.andrade.inventary_management_system_backend.domain.Category;
import com.andrade.inventary_management_system_backend.domain.Product;

import com.andrade.inventary_management_system_backend.dto.ProductDto;
import com.andrade.inventary_management_system_backend.dto.Response;
import com.andrade.inventary_management_system_backend.exception.EmptyResourceException;
import com.andrade.inventary_management_system_backend.exception.NotFoundException;
import com.andrade.inventary_management_system_backend.repository.CategoryRepository;
import com.andrade.inventary_management_system_backend.repository.ProductRepository;
import com.andrade.inventary_management_system_backend.repository.SupplierRepository;
import com.andrade.inventary_management_system_backend.service.ProductService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    // private final SupplierRepository supplierRepository;

    private static final String DIRECTORY_IMAGE = System.getProperty("user.dir") + "/product-image/";
    private final Integer imagaMaxSize = 10 * 1024 * 1024;

    @Override
    public Response createProduct(ProductDto productDto, MultipartFile image) {

        Category categoryProduct = categoryRepository.findById(productDto.getIdCategory())
                .orElseThrow(() -> new NotFoundException("Undefined category"));

        if (image == null || image.isEmpty()) {
            throw new EmptyResourceException("Image undefined");
        }

        String imagePath = saveImage(image);

        Product product = Product.builder()
                .price(productDto.getPrice())
                .sku(productDto.getSku())
                .quantStock(productDto.getQuantStock())
                .description(productDto.getDescription())
                .expireDate(productDto.getExpireDate())
                .category(categoryProduct)
                .imageUrl(imagePath)
                .build();

        productRepository.save(product);
        return Response.builder()
                .status(HttpStatus.CREATED.value())
                .message("Product saved sucessfully")
                .build();
    }

    @Override
    public Response updateProduct(Long id, ProductDto productDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProduct'");
    }

    @Override
    public Response getAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public Response getProductById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProductById'");
    }

    @Override
    public Response delteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delteById'");
    }

    @Override
    public Response searchProduct(String value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchProduct'");
    }

    private String saveImage(MultipartFile image) {

        if (!image.getContentType().startsWith("image/") || image.getSize() > imagaMaxSize) {
            throw new IllegalArgumentException("only image under 10MB is allowed");
        }

        File directory = new File(DIRECTORY_IMAGE);

        if (!directory.exists()) {
            directory.mkdir(); // making sure that dir exist
            log.info("Directory to save image has been created");
        }

        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        String imagePath = DIRECTORY_IMAGE + fileName;

        try {
            File destinationFile = new File(imagePath);
            image.transferTo(destinationFile);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error saving image: " + e.getMessage());
        }
        return imagePath;
    }

}
