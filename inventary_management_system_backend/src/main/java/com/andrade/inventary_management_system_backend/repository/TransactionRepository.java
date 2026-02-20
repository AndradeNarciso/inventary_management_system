package com.andrade.inventary_management_system_backend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.andrade.inventary_management_system_backend.domain.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> ,JpaSpecificationExecutor<Transaction>{

}
