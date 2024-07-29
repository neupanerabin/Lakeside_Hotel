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

    // Declares the class which contains the configuration for CORS.

    private static final Long MAX_AGE = 3600L;  // Maximum age for the CORS configuration
    // Defines a constant for the maximum age (in seconds) that the results of a preflight request can be cached by the client.

    private static final int CORS_FILTER_ORDER = -102;  // Order of the CORS filter
    // Defines a constant for the order of the CORS filter, determining its precedence relative to other filters.

    @Bean
    // Marks the method as a bean producer, adding the returned bean to the Spring application context.

    public FilterRegistrationBean<CorsFilter> customCorsFilter() {
        // Defines a method to configure and register a custom CORS filter.

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Creates a new UrlBasedCorsConfigurationSource to hold the CORS configuration.

        CorsConfiguration config = new CorsConfiguration();
        // Creates a new CorsConfiguration object to specify the CORS settings.

        config.setAllowCredentials(true);
        // Allows credentials (such as cookies and HTTP authentication) to be included in CORS requests.

        config.addAllowedOrigin("http://localhost:5173");
        // Specifies that the origin "http://localhost:5173" is allowed to make CORS requests.

        config.setAllowedHeaders(Arrays.asList(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT));
        // Specifies the HTTP headers that are allowed in CORS requests.

        config.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name()));
        // Specifies the HTTP methods that are allowed in CORS requests.

        config.setMaxAge(MAX_AGE);
        // Sets the maximum age for caching the results of a preflight request.

        source.registerCorsConfiguration("/**", config);
        // Registers the CORS configuration for all URL paths (/**) using the source object.

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        // Creates a new FilterRegistrationBean, wrapping a CorsFilter with the configured source.

        bean.setOrder(CORS_FILTER_ORDER);
        // Sets the order of the filter, ensuring it runs before other filters that might depend on CORS settings.

        return bean;
        // Returns the configured FilterRegistrationBean to be added to the application context.
    }

    //Failed One
//    @Bean
//    public FilterRegistrationBean corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("http://localhost:5173");
//        config.setAllowedHeaders(Arrays.asList(
//                HttpHeaders.AUTHORIZATION,
//                HttpHeaders.CONTENT_TYPE,
//                HttpHeaders.ACCEPT));
//        config.setAllowedMethods(Arrays.asList(
//                HttpMethod.GET.name(),
//                HttpMethod.POST.name(),
//                HttpMethod.PUT.name(),
//                HttpMethod.DELETE.name()));
//        config.setMaxAge(MAX_AGE);
//        source.registerCorsConfiguration("/**", config);
//        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//        bean.setOrder(CORS_FILTER_ORDER);
//        return bean;
//    }

    // Another run successfully


//        @Bean
//        public CorsFilter corsFilter() {
//            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//            CorsConfiguration config = new CorsConfiguration();
//            config.setAllowCredentials(true);
//            config.addAllowedOrigin("http://localhost:5173");
//            config.addAllowedHeader("*");
//            config.addAllowedMethod("*");
//            source.registerCorsConfiguration("/**", config);
//            return new CorsFilter(source);
//        }

}
