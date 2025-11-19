package com.zooracoes_api.security;

import com.zooracoes_api.repositories.UserRepository;
import com.zooracoes_api.entities.UserEntity;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

@Component
@Order(2)
public class JwtAuthenticationFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    
    private final TokenService tokenService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getRequestURI();
        
        // Ignorar endpoints públicos do sistema
        if (path.equals("/auth/login") ||
            path.equals("/health") ||
            path.equals("/version") ||
            path.equals("/favicon.ico")) {
            chain.doFilter(req, res);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        
        logger.debug("JWT Filter - Path: {}, Authorization header: {}", 
                path, authHeader != null ? "Present" : "Missing");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7).trim(); // Remove "Bearer " e espaços

            try {
                logger.debug("Processing JWT token (length: {})", token.length());
                
                // Validar token antes de processar
                if (!tokenService.isTokenValid(token)) {
                    logger.warn("Invalid JWT token");
                    chain.doFilter(req, res);
                    return;
                }
                
                String email = tokenService.getEmailFromToken(token);
                logger.debug("Email extracted from token: {}", email);

                if (email == null || email.isEmpty()) {
                    logger.warn("Email is null or empty from token");
                    chain.doFilter(req, res);
                    return;
                }

                UserEntity user = userRepository.findByEmail(email).orElse(null);

                if (user != null && user.getRole() != null) {
                    String authority = "ROLE_" + user.getRole().name();
                    logger.info("Authenticating user: {} with role: {}", email, authority);
                    
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    user.getEmail(),
                                    null,
                                    Collections.singletonList(new org.springframework.security.core.authority.SimpleGrantedAuthority(authority))
                            );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.debug("Authentication set in SecurityContext for user: {}", email);
                } else {
                    logger.warn("User not found or role is null for email: {}", email);
                }

            } catch (Exception e) {
                logger.error("Error processing JWT token: {} - Exception: {}", e.getMessage(), e.getClass().getSimpleName(), e);
                // Limpar qualquer autenticação anterior em caso de erro
                SecurityContextHolder.clearContext();
            }
        } else {
            logger.debug("No Authorization header or doesn't start with 'Bearer '");
        }

        chain.doFilter(req, res);
    }
}
