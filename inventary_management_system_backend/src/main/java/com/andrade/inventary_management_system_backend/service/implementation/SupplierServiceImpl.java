package com.andrade.inventary_management_system_backend.service.implementation;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.andrade.inventary_management_system_backend.domain.Supplier;
import com.andrade.inventary_management_system_backend.dto.SupplierDto;
import com.andrade.inventary_management_system_backend.dto.Response;
import com.andrade.inventary_management_system_backend.exception.DuplicatedValueException;
import com.andrade.inventary_management_system_backend.exception.NotFoundException;
import com.andrade.inventary_management_system_backend.repository.SupplierRepository;
import com.andrade.inventary_management_system_backend.service.SupplierService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SupplierServiceImpl implements SupplierService {

        private final SupplierRepository supplierRepository;
        private final ModelMapper modelMapper;

        @Override
        public Response createSupplier(SupplierDto supplierDto) {

                Supplier supplier = Supplier.builder()
                                .name(supplierDto.getName())
                                .adress(supplierDto.getAdress())
                                .contactInfo(supplierDto.getContactInfo())
                                .build();
                supplierRepository.save(supplier);

                return Response.builder()
                                .status(HttpStatus.CREATED.value())
                                .message("Supplier saved sucessfully")
                                .build();

        }

        @Override
        public Response getAll() {
                List<Supplier> supplier = supplierRepository.findAll();
                List<SupplierDto> supplierDtos = modelMapper.map(supplier, new TypeToken<List<SupplierDto>>() {
                }.getType());

                return Response.builder()
                                .status(HttpStatus.OK.value())
                                .message("Sucess")
                                .supplierDtos(supplierDtos).build();
        }

        @Override
        public Response getSupplierById(UUID id) {
                Supplier supplier = supplierRepository.findById(id)
                                .orElseThrow(() -> new NotFoundException("Supplier not found"));

                return Response.builder()
                                .status(HttpStatus.OK.value())
                                .supplierDto(modelMapper.map(supplier, SupplierDto.class))
                                .build();
        }

        @Override
        public Response updateSupplier(UUID id, SupplierDto supplierDto) {
                Supplier savedSupplier = supplierRepository.findById(id)
                                .orElseThrow(() -> new NotFoundException("Supplier not found"));

                if (supplierDto.getAdress() != null) {
                        savedSupplier.setAdress(supplierDto.getAdress());
                }

                if (supplierDto.getName() != null) {
                        savedSupplier.setName(supplierDto.getName());
                }

                if (savedSupplier.getName().equals(supplierDto.getName())) {
                        throw new DuplicatedValueException("Supplier's alredy exists");
                }

                if (supplierDto.getContactInfo() != null) {
                        savedSupplier.setContactInfo(supplierDto.getContactInfo());
                }

                return Response.builder()
                                .status(HttpStatus.OK.value())
                                .supplierDto(modelMapper.map(savedSupplier, SupplierDto.class))
                                .build();
        }

        @Override
        public Response deleteSupplier(UUID id) {

                if (!supplierRepository.existsById(id)) {
                        throw new NotFoundException("Supplier do not exists");
                }
                supplierRepository.deleteById(id);
                return Response.builder()
                                .status(HttpStatus.OK.value())
                                .message("Supplier deleted")
                                .build();

        }

}
