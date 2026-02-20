package com.andrade.inventary_management_system_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andrade.inventary_management_system_backend.domain.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
    
}
