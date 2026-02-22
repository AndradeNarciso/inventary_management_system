package com.andrade.inventary_management_system_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.andrade.inventary_management_system_backend.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
