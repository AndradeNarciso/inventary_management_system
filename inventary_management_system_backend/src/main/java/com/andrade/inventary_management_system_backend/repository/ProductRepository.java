package com.andrade.inventary_management_system_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.andrade.inventary_management_system_backend.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByNameContainingOrDescriptionContaining(String name, String description);
}
