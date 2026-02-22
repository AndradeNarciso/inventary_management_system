package com.andrade.inventary_management_system_backend.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
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

        try {
            String token = getTokenFromRequest(request);
            String email = jwtUtil.getUserNameFromToken(token);
            UserDetails userDetails = customUserDetailService.loadUserByUsername(email);

            if (StringUtils.hasText(email) && jwtUtil.isValidToken(token, userDetails)) {
                log.info("Valid token found from user with email: {}", email);
                UsernamePasswordAuthenticationToken autenticaionToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                autenticaionToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(autenticaionToken);
            }
        } catch (Exception e) {
            log.info("Can not Authenticate user ");
        }

        filterChain.doFilter(request, response);

    }

    private String getTokenFromRequest(HttpServletRequest request) {

        String header = request.getHeader("Authorization");

        if (!StringUtils.hasText(header)) {
            throw new HeaderNotFoundException("Authorization header not found");
        }
        if (!header.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header format");
        }
        return header.substring("Bearer ".length());

    }

}
