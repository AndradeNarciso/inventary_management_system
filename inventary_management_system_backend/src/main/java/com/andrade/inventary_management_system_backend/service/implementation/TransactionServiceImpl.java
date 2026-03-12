package com.andrade.inventary_management_system_backend.service.implementation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.andrade.inventary_management_system_backend.domain.Product;
import com.andrade.inventary_management_system_backend.domain.Supplier;
import com.andrade.inventary_management_system_backend.domain.Transaction;
import com.andrade.inventary_management_system_backend.domain.User;
import com.andrade.inventary_management_system_backend.dto.Response;
import com.andrade.inventary_management_system_backend.dto.TransactionDto;
import com.andrade.inventary_management_system_backend.dto.TransactionRequest;
import com.andrade.inventary_management_system_backend.enums.TransactionStatus;
import com.andrade.inventary_management_system_backend.enums.TransactionType;
import com.andrade.inventary_management_system_backend.exception.NotFoundException;
import com.andrade.inventary_management_system_backend.repository.ProductRepository;
import com.andrade.inventary_management_system_backend.repository.SupplierRepository;
import com.andrade.inventary_management_system_backend.repository.TransactionRepository;
import com.andrade.inventary_management_system_backend.service.TransactionService;
import com.andrade.inventary_management_system_backend.service.UserService;
import com.andrade.inventary_management_system_backend.specification.TransactionFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    @Override
    public Response purchase(TransactionRequest transactionRequest) {

        UUID idSupplier = transactionRequest.supplierId();

        Supplier supplier = supplierRepository.findById(idSupplier)
                .orElseThrow(() -> new NotFoundException("Supplier does not exists"));

        Long idProduct = transactionRequest.productId();

        Product product = productRepository.findById(idProduct)
                .orElseThrow(() -> new NotFoundException("product does not exists "));

        Integer quantity = transactionRequest.quant();

        User user = userService.getCurrentLoggedInUser();

        product.setQuantStock(product.getQuantStock() + quantity);
        productRepository.save(product);

        Transaction transaction = Transaction.builder()
                .product(product)
                .transactionStatus(TransactionStatus.COMPLETED)
                .transactionType(TransactionType.PURCHASE)
                .user(user)
                .supplier(supplier)
                .totalProduct(quantity)
                .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)))
                .note(transactionRequest.note())
                .description(transactionRequest.description())
                .build();

        transactionRepository.save(transaction);

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("Purchase done sucessfully")
                .build();

    }

    @Override
    public Response sell(TransactionRequest transactionRequest) {

        Long idProduct = transactionRequest.productId();
        Product product = productRepository.findById(idProduct)
                .orElseThrow(() -> new NotFoundException("product does not exists "));

        Integer quantity = transactionRequest.quant();
        product.setQuantStock(product.getQuantStock() - quantity);

        User user = userService.getCurrentLoggedInUser();

        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.SALE)
                .transactionStatus(TransactionStatus.COMPLETED)
                .user(user)
                .totalProduct(quantity)
                .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)))
                .note(transactionRequest.note())
                .description(transactionRequest.description())
                .build();

        transactionRepository.save(transaction);

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("Sale done sucessfully")
                .build();

    }

    @Override
    public Response returnToSupplier(TransactionRequest transactionRequest) {

        Long idProduct = transactionRequest.productId();

        Product product = productRepository.findById(idProduct)
                .orElseThrow(() -> new NotFoundException("product does not exists "));

        Integer quantity = transactionRequest.quant();

        UUID idSupplier = transactionRequest.supplierId();

        Supplier supplier = supplierRepository.findById(idSupplier)
                .orElseThrow(() -> new NotFoundException("Supplier does not exists"));

        product.setQuantStock(product.getQuantStock() - quantity);
        productRepository.save(product);

        User user = userService.getCurrentLoggedInUser();

        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.RETURN_TO_SUPPLIER)
                .transactionStatus(TransactionStatus.PROCESSING)
                .user(user)
                .totalProduct(quantity)
                .totalPrice(BigDecimal.ZERO)
                .note(transactionRequest.note())
                .description(transactionRequest.description())
                .supplier(supplier)
                .build();

        transactionRepository.save(transaction);

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("product return to supplier in process")
                .build();
    }

    @Override
    public Response getAllTransaction(String filter, Pageable page) {

        Specification<Transaction> spec = TransactionFilter.filterByValue(filter);

        Page<Transaction> pagaTransactionByFilterList = transactionRepository.findAll(spec, page);
        List<TransactionDto> transactionDtoByFilterList = modelMapper.map(pagaTransactionByFilterList.getContent(),
                new TypeToken<List<TransactionDto>>() {
                }.getType());

        return Response.builder()
                .status(HttpStatus.OK.value())
                .transactionDtos(transactionDtoByFilterList)
                .totalElement(pagaTransactionByFilterList.getTotalElements())
                .totalPage(pagaTransactionByFilterList.getTotalPages())
                .build();

    }

    @Override
    public Response getTransactionById(UUID id) {

        Transaction savedTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction was not found "));

        return Response.builder()
                .status(HttpStatus.OK.value())
                .transactionDto(modelMapper.map(savedTransaction, TransactionDto.class))
                .build();

    }

    @Override
    public Response getByMonthAndYear(int month, int year) {

        Specification<Transaction> spec = TransactionFilter.monthAndYear(month, year);

        List<Transaction> transactionsByMonthAndYearList = transactionRepository.findAll(spec);
        List<TransactionDto> transactionDtoByFilterList = modelMapper.map(transactionsByMonthAndYearList,
                new TypeToken<List<TransactionDto>>() {
                }.getType());

        return Response.builder()
                .status(HttpStatus.OK.value())
                .transactionDtos(transactionDtoByFilterList)
                .message("Sucess")
                .build();
    }

    @Override
    public Response UpdateTransactionStatus(UUID id, TransactionStatus status) {

        Transaction savedTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction was not found "));

        savedTransaction.setTransactionStatus(status);
        savedTransaction.setUpdateAt(LocalDateTime.now());
        transactionRepository.save(savedTransaction);
        
        log.info("A Transaction was updated");

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("Transaction updated sucessfully")
                .build();

    }

}
