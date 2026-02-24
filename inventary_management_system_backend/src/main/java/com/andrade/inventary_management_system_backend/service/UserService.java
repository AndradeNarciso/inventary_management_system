package com.andrade.inventary_management_system_backend.service;

import java.util.UUID;

import com.andrade.inventary_management_system_backend.domain.User;
import com.andrade.inventary_management_system_backend.dto.LoginRequest;
import com.andrade.inventary_management_system_backend.dto.RegisterRequest;
import com.andrade.inventary_management_system_backend.dto.Response;
import com.andrade.inventary_management_system_backend.dto.UserDto;

public interface UserService {

    Response register(RegisterRequest registerRequest);

    Response login(LoginRequest loginRequest);

    Response getAll();

    Response getUserById(UUID id);

    Response updateUser(UUID id, UserDto user);

    Response deleteUser(UUID id);

    Response getUserTransaction(UUID id);

    User getCurrentLoggedInUser();

}
