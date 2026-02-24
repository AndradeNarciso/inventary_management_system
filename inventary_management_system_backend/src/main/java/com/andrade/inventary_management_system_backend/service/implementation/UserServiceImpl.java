package com.andrade.inventary_management_system_backend.service.implementation;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.andrade.inventary_management_system_backend.domain.User;
import com.andrade.inventary_management_system_backend.dto.LoginRequest;
import com.andrade.inventary_management_system_backend.dto.RegisterRequest;
import com.andrade.inventary_management_system_backend.dto.Response;
import com.andrade.inventary_management_system_backend.dto.UserDto;
import com.andrade.inventary_management_system_backend.enums.Role;
import com.andrade.inventary_management_system_backend.exception.RequiredRoleException;
import com.andrade.inventary_management_system_backend.repository.UserRepository;
import com.andrade.inventary_management_system_backend.security.JwtUtil;
import com.andrade.inventary_management_system_backend.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;

    @Override
    public Response register(RegisterRequest registerRequest) {

        if (registerRequest.role() == null) {
            throw new RequiredRoleException("Role required");
        }

        try {
            Role role = Role.valueOf(registerRequest.role().name());
            User user = User.builder()
                    .name(registerRequest.name())
                    .email(registerRequest.email())
                    .password(passwordEncoder.encode(registerRequest.password()))
                    .role(role)
                    .build();

            userRepository.save(user);
        } catch (Exception e) {
            throw new RequiredRoleException("Invalid Role");
        }

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("User sucessfuly registered")
                .build();

    }

    @Override
    public Response login(LoginRequest loginRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

    @Override
    public Response getAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public Response getUserById(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserById'");
    }

    @Override
    public Response updateUser(UUID id, UserDto user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    public Response deleteUser(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }

    @Override
    public Response getUserTransaction(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserTransaction'");
    }

    @Override
    public User getCurrentLoggedInUser() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCurrentLoggedInUser'");
    }

}
