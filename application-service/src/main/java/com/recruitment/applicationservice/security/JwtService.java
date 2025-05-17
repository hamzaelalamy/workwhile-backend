package com.recruitment.applicationservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret:404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970}")
    private String secretKey;

    @Value("${jwt.expiration:86400000}") // Default: 24 hours in milliseconds
    private long jwtExpiration;

    /**
     * Extracts the user ID from the JWT token
     * @param token JWT token
     * @return User ID from token subject
     */
    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Alias for extractUserId - maintains compatibility with Spring Security
     */
    public String extractUsername(String token) {
        return extractUserId(token);
    }

    /**
     * Extracts the role from the token's claims
     * @param token JWT token
     * @return Role from token claims
     */
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    /**
     * Generic method to extract any claim from the token
     * @param token The JWT token
     * @param claimsResolver Function to extract specific claim
     * @return The extracted claim value
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a JWT token for the given UserDetails
     * @param userDetails User details
     * @return Signed JWT token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        // Add role to claims if available
        userDetails.getAuthorities().stream()
                .findFirst()
                .ifPresent(authority -> claims.put("role", authority.getAuthority()));

        return generateToken(claims, userDetails.getUsername());
    }

    /**
     * Generates a JWT token with custom claims
     * @param extraClaims Additional claims to include in token
     * @param userId User ID to set as subject
     * @return Signed JWT token
     */
    public String generateToken(Map<String, Object> extraClaims, String userId) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates token against user details
     * @param token JWT token to validate
     * @param userDetails User details to validate against
     * @return True if token is valid for the given user
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String extractedUserId = extractUserId(token);
        return (extractedUserId.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Checks if the token is valid for the given user ID
     * @param token JWT token to check
     * @param userId User ID to validate against
     * @return True if token is valid for this user ID
     */
    public boolean isTokenValid(String token, String userId) {
        final String extractedUserId = extractUserId(token);
        return (extractedUserId.equals(userId)) && !isTokenExpired(token);
    }

    /**
     * Checks if the token is expired
     * @param token JWT token
     * @return True if token expiration date is before current date
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the token
     * @param token JWT token
     * @return Expiration date
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts all claims from the token
     * @param token JWT token
     * @return All claims
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Creates a signing key from the secret
     * @return Key for signing and verifying JWT tokens
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}