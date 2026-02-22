package com.andrade.inventary_management_system_backend.repository;

import java.util.Locale.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  CategoryRepository  extends JpaRepository<Category,Long>{
    
}
