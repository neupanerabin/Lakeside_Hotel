package com.lakesidedemo.lakesideHotel.security.jwt;

import com.lakesidedemo.lakesideHotel.security.user.HotelUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

/**
 * @author : rabin
 *
 * Utility class for generating, parsing, and validating JWT tokens.
 */
@Component
public class JwtUtils {
    // Logger for logging events related to JWT operations
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    // Secret key for signing the JWT, injected from application properties
    @Value("${auth.token.jwtSecret}")
    private String jwtSecret;

    // JWT expiration time, injected from application properties
    @Value("${auth.token.expirationInMils}")
    private int jwtExpirationTime;

    /**
     * Generates a JWT token for the authenticated user.
     *
     * @param authentication The authentication object containing user details.
     * @return The generated JWT token as a string.
     */
    public String generateJwtTokenForUser(Authentication authentication) {
        // Extract user details from the authentication object
        HotelUserDetails userPrincipal = (HotelUserDetails) authentication.getPrincipal();

        // Extract roles from the user details
        List<String> roles = userPrincipal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList();

        // Build and return the JWT token
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationTime))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Decodes the secret key from Base64 format and returns it as a Key object.
     *
     * @return The decoded secret key.
     */
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    /**
     * Extracts the username (subject) from the given JWT token.
     *
     * @param token The JWT token.
     * @return The username extracted from the token.
     */
    public String getUserNameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Validates the given JWT token.
     *
     * @param token The JWT token to validate.
     * @return True if the token is valid, false otherwise.
     */
    public boolean validateToken(String token) {
        try {
            // Parse the token to validate it
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
            return true;
        } catch (MalformedJwtException e) {
            // Log error if the token is malformed
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            // Log error if the token is expired
            logger.error("Expired JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            // Log error if the token is unsupported
            logger.error("Unsupported JWT token: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            // Log error if the token claims string is empty
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        // Return false if token validation fails
        return false;
    }
}
