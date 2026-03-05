package com.andrade.inventary_management_system_backend.service.implementation;

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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    // private final SupplierRepository supplierRepository;

    private static final String DIRECTORY_IMAGE = System.getProperty("user.dir") + "/product-image/";

    @Override
    public Response createProduct(ProductDto productDto, MultipartFile image) {

        Category categoryProduct = categoryRepository.findById(productDto.getIdCategory())
                .orElseThrow(() -> new NotFoundException("Undefined category"));

        Product product = Product.builder()
                .price(productDto.getPrice())
                .sku(productDto.getSku())
                .quantStock(productDto.getQuantStock())
                .description(productDto.getDescription())
                .expireDate(productDto.getExpireDate())
                .category(categoryProduct)
                .build();

        if (image == null || image.isEmpty()) {
            throw new EmptyResourceException("Image undefined");
        }
        productRepository.save(product);

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

}
