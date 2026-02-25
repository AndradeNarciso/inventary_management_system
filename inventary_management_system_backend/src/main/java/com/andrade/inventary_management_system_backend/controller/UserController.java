package com.andrade.inventary_management_system_backend.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andrade.inventary_management_system_backend.domain.User;
import com.andrade.inventary_management_system_backend.dto.Response;
import com.andrade.inventary_management_system_backend.dto.UserDto;
import com.andrade.inventary_management_system_backend.service.implementation.UserServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllUser() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getAll());
    }

    @PostMapping("/{id}")
    public ResponseEntity<Response> loginUser(@PathVariable("id") UUID userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUserById(userId));

    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Response> updateUser(@PathVariable("id") UUID userId, @Valid @RequestBody UserDto userDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.updateUser(userId, userDto));

    }

    @GetMapping("/current-user")
    public ResponseEntity<User> getCurrentUser() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getCurrentLoggedInUser());

    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteUserById(@PathVariable("id") UUID userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.deleteUser(userId));

    }

    @GetMapping("/transaction/{id}")
    public ResponseEntity<Response> getUserTransaction(@PathVariable("id") UUID userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUserTransaction(userId));

    }

}
