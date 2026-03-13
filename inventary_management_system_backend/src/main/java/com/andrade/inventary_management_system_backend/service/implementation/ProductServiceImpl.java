package com.andrade.inventary_management_system_backend.service.implementation;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.andrade.inventary_management_system_backend.domain.Category;
import com.andrade.inventary_management_system_backend.domain.Product;

import com.andrade.inventary_management_system_backend.dto.ProductDto;
import com.andrade.inventary_management_system_backend.dto.Response;
import com.andrade.inventary_management_system_backend.exception.CustomBadRequestException;
import com.andrade.inventary_management_system_backend.exception.DuplicatedValueException;
import com.andrade.inventary_management_system_backend.exception.EmptyResourceException;
import com.andrade.inventary_management_system_backend.exception.NotFoundException;
import com.andrade.inventary_management_system_backend.exception.SerializationException;
import com.andrade.inventary_management_system_backend.repository.CategoryRepository;
import com.andrade.inventary_management_system_backend.repository.ProductRepository;
import com.andrade.inventary_management_system_backend.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(rollbackOn = Exception.class)

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final ObjectMapper mapper; // using that to mapper only String productDTO cuz modelMapper got some kind
                                       // issue, and i don't think i should take any longer

    private static final String DIRECTORY_IMAGE = System.getProperty("user.dir") + "/product-image/";
    private final Integer imagaMaxSize = 10 * 1024 * 1024;

    @Override
    public Response createProduct(String productDtoStringValue, MultipartFile image) {

        if (image == null || image.isEmpty()) {
            throw new EmptyResourceException("Image undefined");
        }
        try {
            ProductDto productDto = mapper.readValue(productDtoStringValue, ProductDto.class);

           

            Category categoryProduct = categoryRepository.findById(productDto.getIdCategory())
                    .orElseThrow(() -> new NotFoundException("Undefined category"));

            if (productRepository.existsBySku(productDto.getSku())) {
                throw new DuplicatedValueException("Sku already exist");

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

        } catch (DuplicatedValueException ex) {
            throw new DuplicatedValueException(ex.getMessage());

        }

        catch (Exception e) {
            throw new CustomBadRequestException(
                    "Cannot serialize product. Ensure all fields in the product data are correct and valid");
        }

    }

    @Override
    public Response updateProduct(String productDtoStringValue, MultipartFile image) {

        try {
            ProductDto productDto = mapper.readValue(productDtoStringValue, ProductDto.class);

            Product product = productRepository.findById(productDto.getIdProduct())
                    .orElseThrow(() -> new NotFoundException("Producto was not found"));

            if (image != null && !image.isEmpty()) {
                String newImagePath = saveImage(image);
                product.setImageUrl(newImagePath);
            }

            if (productDto.getIdCategory() != null && productDto.getIdCategory() > 0) {

                Category newCategory = categoryRepository.findById(productDto.getIdCategory())
                        .orElseThrow(() -> new NotFoundException("Category was not found"));

                product.setCategory(newCategory);
            }

            if (productDto.getDescription() != null && !productDto.getDescription().isBlank()) {
                product.setDescription(productDto.getDescription());
            }

            if (productDto.getName() != null && !productDto.getName().isBlank()) {
                product.setName(productDto.getName());
            }

            if (productDto.getSku() != null && !productDto.getSku().isBlank()) {
                product.setSku(productDto.getSku());
            }

            if (productDto.getPrice() != null && productDto.getPrice().compareTo(BigDecimal.ZERO) >= 0) {
                product.setPrice(productDto.getPrice());
            }

            if (productDto.getQuantStock() != null && productDto.getQuantStock() >= 0) {
                product.setQuantStock(productDto.getQuantStock());
            }

            productRepository.save(product);

            return Response.builder()
                    .status(HttpStatus.OK.value())
                    .message("Product updated sucessfully")
                    .build();
        } catch (JsonMappingException e) {
            throw new SerializationException("Error trying mapping Product");
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error trying processing Product");
        }

    }

    @Override
    public Response getAll() {

        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = modelMapper.map(products, new TypeToken<List<ProductDto>>() {
        }.getType());

        return Response.builder()
                .status(HttpStatus.CREATED.value())
                .productDtos(productDtos)
                .build();
    }

    @Override
    public Response getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product was not found"));

        return Response.builder()
                .status(HttpStatus.OK.value())
                .productDto(modelMapper.map(product, ProductDto.class))
                .build();
    }

    @Override
    public Response deleteById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("Product do not exists");
        }
        categoryRepository.deleteById(id);

        return Response.builder().status(HttpStatus.OK.value())
                .message("Product deleted")
                .build();

    }

    @Override
    public Response searchProduct(String value) {

        List<Product> products = productRepository.findByNameContainingOrDescriptionContaining(value, value);

        if (products.isEmpty()) {
            throw new NotFoundException("None product was found");
        }
        List<ProductDto> productDtos = modelMapper.map(products, new TypeToken<List<ProductDto>>() {
        }.getType());

        return Response.builder()
                .status(HttpStatus.OK.value())
                .productDtos(productDtos)
                .build();

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
