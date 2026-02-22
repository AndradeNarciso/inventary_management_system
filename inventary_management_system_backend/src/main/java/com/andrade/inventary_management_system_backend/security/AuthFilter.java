package com.andrade.inventary_management_system_backend.security;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.andrade.inventary_management_system_backend.exception.HeaderNotFoundException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = getTokenFromRequest(request);

    }

    private String getTokenFromRequest(HttpServletRequest request) {

        String header = request.getHeader("Authorization");

        if (header == null || header.isBlank()) {
            throw new HeaderNotFoundException("Authorization header not found");
        }
        if (!header.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header format");
        }

        String extractedToken = header.substring("Bearer ".length());

        throw new UnsupportedOperationException("Unimplemented method 'getTokenFromRequest'");
    }

}
