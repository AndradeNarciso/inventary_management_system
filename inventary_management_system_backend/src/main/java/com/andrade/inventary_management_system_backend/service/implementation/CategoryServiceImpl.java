package com.andrade.inventary_management_system_backend.service.implementation;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.andrade.inventary_management_system_backend.domain.Category;
import com.andrade.inventary_management_system_backend.dto.CategoryDto;
import com.andrade.inventary_management_system_backend.dto.Response;
import com.andrade.inventary_management_system_backend.exception.NotFoundException;
import com.andrade.inventary_management_system_backend.repository.CategoryRepository;
import com.andrade.inventary_management_system_backend.service.CategoryService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
        
        private final CategoryRepository categoryRepository;
        private final ModelMapper modelMapper;

        @Override
        public Response createCategory(CategoryDto categoryDto) {
                Category category = Category.builder()
                                .name(categoryDto.getName())
                                .build();
                categoryRepository.save(category);

                return Response.builder()
                                .status(HttpStatus.CREATED.value())
                                .message("Category saved sucessfully")
                                .build();

        }

        @Override
        public Response getAll() {

                List<Category> users = categoryRepository.findAll(Sort.by(Sort.Direction.DESC));
                List<CategoryDto> categoryDtos = modelMapper.map(users, new TypeToken<List<CategoryDto>>() {
                }.getType());

                return Response.builder()
                                .status(HttpStatus.OK.value())
                                .message("Sucess")
                                .categoryDtos(categoryDtos).build();
        }

        @Override
        public Response getCategoryById(Long id) {
                Category category = categoryRepository.findById(id)
                                .orElseThrow(() -> new NotFoundException("Category not found"));

                return Response.builder()
                                .status(HttpStatus.OK.value())
                                .categoryDto(modelMapper.map(category, CategoryDto.class))
                                .build();
        }

        @Override
        public Response updateCaterory(Long id, CategoryDto categoryDto) {
                Category savedCategory = categoryRepository.findById(id)
                                .orElseThrow(() -> new NotFoundException("Category not found"));

                if (categoryDto.getName() != null) {
                        savedCategory.setName(categoryDto.getName());
                        categoryRepository.save(savedCategory);
                        return Response.builder()
                                        .status(HttpStatus.OK.value())
                                        .categoryDto(modelMapper.map(savedCategory, CategoryDto.class))
                                        .build();

                }
                return Response.builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .message("Could not update Category")
                                .build();

        }

        @Override
        public Response deleteCategory(long id) {
                if (!categoryRepository.existsById(id)) {
                        throw new NotFoundException("User do not exists");
                }
                categoryRepository.deleteById(id);

                return Response.builder().status(HttpStatus.OK.value())
                                .message("Category deleteda")
                                .build();

        }

}
