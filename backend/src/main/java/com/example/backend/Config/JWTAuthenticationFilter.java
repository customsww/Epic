package com.example.backend.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.backend.Service.CustomUserDetailsService;

import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService userDetailsService;

    public JWTAuthenticationFilter(JwtUtils jwtUtils, CustomUserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);  // Loại bỏ "Bearer " khỏi token

            if (jwtUtils.validateToken(token)) {
                String username = jwtUtils.getUserEmailFromJWT(token);
                if (username != null) {
                    // Load UserDetails
                    org.springframework.security.core.userdetails.UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    // Create Authentication object
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,  // You can set credentials here if needed (e.g. password)
                            userDetails.getAuthorities()  // Pass user roles/authorities
                    );

                    // Cast Authentication to UsernamePasswordAuthenticationToken to access setDetails()
                    if (authentication instanceof UsernamePasswordAuthenticationToken) {
                        UsernamePasswordAuthenticationToken tokenAuth = (UsernamePasswordAuthenticationToken) authentication;

                        // Set the details using the WebAuthenticationDetailsSource
                        tokenAuth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        // Set the authentication context
                        SecurityContextHolder.getContext().setAuthentication(tokenAuth);
                    }
                }
            } else {
                // Token không hợp lệ hoặc hết hạn, clear context để bảo mật
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}
