package com.lakesidedemo.lakesideHotel.security.jwt;

/*
 * @author : rabin
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtAuthEntryPoint is used to handle unauthorized access attempts.
 * It implements the AuthenticationEntryPoint interface and provides a custom response for unauthorized requests.
 */
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    // Logger for logging unauthorized access attempts
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthEntryPoint.class);

    // ObjectMapper is used to convert Java objects to JSON and vice versa
    @Autowired
    private ObjectMapper mapper;

    /**
     * Handles unauthorized access attempts by returning a JSON response with an error message.
     *
     * @param request The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @param authException The exception thrown when authentication fails
     * @throws IOException If an input or output exception occurs
     * @throws ServletException If a servlet exception occurs
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        // Log the unauthorized access attempt with the request URI
        logger.error("Unauthorized error: {}. Path: {}", authException.getMessage(), request.getRequestURI());

        // Set the response content type to JSON
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // Set the response status to 401 Unauthorized
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Create a map to hold the response body data
        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", authException.getMessage());
        body.put("path", request.getServletPath());

        // Write the response body as JSON using ObjectMapper
        mapper.writeValue(response.getOutputStream(), body);
    }
}
