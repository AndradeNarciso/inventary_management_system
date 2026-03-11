package com.andrade.inventary_management_system_backend.service.implementation;

import java.util.UUID;

import org.modelmapper.ModelMapper;
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
import com.andrade.inventary_management_system_backend.exception.NotFoundException;
import com.andrade.inventary_management_system_backend.repository.ProductRepository;
import com.andrade.inventary_management_system_backend.repository.SupplierRepository;
import com.andrade.inventary_management_system_backend.repository.TransactionRepository;
import com.andrade.inventary_management_system_backend.service.TransactionService;
import com.andrade.inventary_management_system_backend.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*   @Positive Long productId,
        @Min(value = 0) Integer quant,
        UUID supplierId,
        @NotBlank String note,
        @NotBlank String description */

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'purchase'");
    }

    @Override
    public Response sell(TransactionRequest transactionRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sell'");
    }

    @Override
    public Response returnToSupplier(TransactionRequest transactionRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'returnToSupplier'");
    }

    @Override
    public Response getAllTransaction(int page, int size, String filter) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllTransaction'");
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByMonthAndYear'");
    }

    @Override
    public Response UpdateTransactionStatus(UUID id, TransactionStatus status) {

        Transaction savedTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction was not found "));

        savedTransaction.setTransactionStatus(status);
        transactionRepository.save(savedTransaction);
        log.info("A Transaction was updated");

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("Transaction updated sucessfully")
                .build();

    }

}
