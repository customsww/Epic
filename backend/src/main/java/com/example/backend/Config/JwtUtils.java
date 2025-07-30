package com.example.backend.Config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.example.backend.Dto.UserDTO;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {
    private String jwtSecret;
    private long jwtExpiration;

    @Autowired
    public void setEnvironment(Environment environment) {
        this.jwtSecret = environment.getProperty("jwt.signerKey",
                "K1YJD9Mt3V3wklqFpuYQXZhKSMdh6UZyLBb/nvXKBk1Lw3yXtRYTcpLqwOa8ACsc");
        this.jwtExpiration = Long.parseLong(environment.getProperty("jwt.expirationTime", "86400000"));
    }

    
    public String generateToken(UserDTO user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("id", user.getId())
                .claim("fullname", user.getFullname())
                .claim("username", user.getEmail())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

    }

    public void addTokenToCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("auth_token", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) (jwtExpiration / 1000));
        response.addCookie(cookie);
    }

    public String getUserEmailFromJWT(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            log.info("👉 Token claims:");
            log.info("Subject (username): {}", claims.getSubject());
            log.info("Email (from 'username' claim): {}", claims.get("username"));
            log.info("User ID: {}", claims.get("id"));
            log.info("Gender: {}", claims.get("gender"));
            log.info("Role: {}", claims.get("role"));
            log.info("Issued At: {}", claims.getIssuedAt());
            log.info("Expiration: {}", claims.getExpiration());
            return claims.getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid or expired JWT token", e);
            return null;
        }
    }

    public boolean validateToken(String authToken) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(authToken)
                    .getBody(); // lấy dữ liệu trong token

            // ✅ In ra dữ liệu bên trong token
            log.info("JWT Token is valid");
            log.info("Email (subject): {}", claims.getSubject());
            log.info("User ID: {}", claims.get("id"));
            log.info("Role: {}", claims.get("role"));
            log.info("Issued at: {}", claims.getIssuedAt());
            log.info("Expiration: {}", claims.getExpiration());
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);

            return true;
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired", e);
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported", e);
        } catch (MalformedJwtException e) {
            log.error("JWT token is malformed", e);
        } catch (JwtException | IllegalArgumentException e) {
            log.error("JWT validation error: {}", e.getMessage(), e);
        }
        return false;
    }

}
