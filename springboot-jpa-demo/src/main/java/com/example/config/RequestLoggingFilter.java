package com.example.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.entity.User;
import com.example.repository.UserRepository;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger("USER_REQUEST_LOG");

    public RequestLoggingFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String token = null;
        String username = "Anonymous";

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            User user = userRepository.findByToken(token);
            if (user == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write(
                        "{\"status\":401,\"message\":\"Invalid token\",\"data\":null}");
                response.getWriter().flush();
                return;
            }

            username = user.getUsername();
        }

        // Log the request to file
        logger.info("{} | User: {} | Method: {} | Path: {}",
                LocalDateTime.now(),
                username,
                request.getMethod(),
                request.getRequestURI());

        filterChain.doFilter(request, response);
    }
}
