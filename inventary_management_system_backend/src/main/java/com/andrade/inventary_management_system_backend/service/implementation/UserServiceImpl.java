package com.andrade.inventary_management_system_backend.service.implementation;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.andrade.inventary_management_system_backend.domain.User;
import com.andrade.inventary_management_system_backend.dto.LoginRequest;
import com.andrade.inventary_management_system_backend.dto.RegisterRequest;
import com.andrade.inventary_management_system_backend.dto.Response;
import com.andrade.inventary_management_system_backend.dto.UserDto;
import com.andrade.inventary_management_system_backend.enums.Role;
import com.andrade.inventary_management_system_backend.exception.InvalidCredentialException;
import com.andrade.inventary_management_system_backend.exception.NotFoundException;
import com.andrade.inventary_management_system_backend.repository.UserRepository;
import com.andrade.inventary_management_system_backend.security.JwtUtil;
import com.andrade.inventary_management_system_backend.service.UserService;

import jakarta.transaction.Transactional;
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
    @Transactional(rollbackOn = Exception.class)
    public Response register(RegisterRequest registerRequest) {

        try {

            Role role = Role.valueOf(registerRequest.role().name());
            User user = User.builder()
                    .name(registerRequest.name())
                    .email(registerRequest.email())
                    .password(passwordEncoder.encode(registerRequest.password()))
                    .phoneNumber(registerRequest.phoneNumber())
                    .role(role)
                    .build();

            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Bad Request");
        }

        log.info("New user added");
        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("User sucessfuly registered")
                .build();

    }

    @Override
    public Response login(LoginRequest loginRequest) {

        User loggedUser = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new NotFoundException("User was not found"));

        if (!passwordEncoder.matches(loginRequest.password(), loggedUser.getPassword())) {
            throw new InvalidCredentialException("Invalid Passwords");
        }
        String token = jwtUtil.generateToken(loggedUser.getEmail());
        return Response.builder()
                .token(token)
                .message("User logged in")
                .role(loggedUser.getRole())
                .expirationTime("5h")
                .build();
    }

    @Override
    public Response getAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = modelMapper.map(users, new TypeToken<List<UserDto>>() {
        }.getType());
        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("Sucess")
                .users(userDtos).build();
    }

    @Override
    public Response getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User was not found"));

        return Response.builder()
                .status(HttpStatus.OK.value())
                .user(modelMapper.map(user, UserDto.class))
                .build();
    }

    @Override
    public Response updateUser(UUID id, UserDto userDto) {

        User savedUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User was not found"));

        if (userDto.getName() != null) {
            savedUser.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            savedUser.setEmail(userDto.getEmail());
        }
        if (userDto.getPhoneNumber() != null) {
            savedUser.setPhoneNumber(userDto.getPhoneNumber());
        }
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            savedUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        Role role = Role.valueOf(userDto.getRole().name());
        savedUser.setRole(role);

        userRepository.save(savedUser);

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("User sucessfuly updated")
                .build();

    }

    @Override
    public Response deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User do not exists");
        }
        userRepository.deleteById(id);
        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("User deleted")
                .build();
    }

    @Override
    public Response getUserTransaction(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User was not found"));
        UserDto userDto = modelMapper.map(user, UserDto.class);

        userDto.getTransactions().forEach(transactionDto -> {
            transactionDto.setSupplier(null);
            transactionDto.setUser(null);
        });

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("sucess")
                .user(userDto)
                .build();
    }

    @Override
    public User getCurrentLoggedInUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Was not found loggend user"));

    }

}
