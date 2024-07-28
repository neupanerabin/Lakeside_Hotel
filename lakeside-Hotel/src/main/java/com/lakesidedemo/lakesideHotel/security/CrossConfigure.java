package com.lakesidedemo.lakesideHotel.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

@Configuration
@EnableWebMvc
public class CrossConfigure {

    private static final Long MAX_AGE = 3600L;  // Maximum age for the CORS configuration
    private static final int CORS_FILTER_ORDER = -102;  // Order of the CORS filter

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        // Create a source for URL-based CORS configuration
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Create a new CORS configuration
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);  // Allow credentials (cookies, authorization headers, etc.)
        config.addAllowedOrigin("http://localhost:5123");  // Allowed origin
        config.setAllowedHeaders(Arrays.asList(
                HttpHeaders.AUTHORIZATION,  // Allow Authorization header
                HttpHeaders.CONTENT_TYPE,  // Allow Content-Type header
                HttpHeaders.ACCEPT));  // Allow Accept header
        config.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),  // Allow GET requests
                HttpMethod.POST.name(),  // Allow POST requests
                HttpMethod.PUT.name(),  // Allow PUT requests
                HttpMethod.DELETE.name()));  // Allow DELETE requests
        config.setMaxAge(MAX_AGE);  // Set the maximum age for the CORS configuration

        // Register the CORS configuration for all endpoints
        source.registerCorsConfiguration("/**", config);

        // Create and configure the FilterRegistrationBean
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(CORS_FILTER_ORDER);  // Set the order of the filter

        return bean;
    }
}
