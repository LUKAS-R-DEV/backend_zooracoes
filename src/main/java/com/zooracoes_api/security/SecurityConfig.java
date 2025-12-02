package com.zooracoes_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                    // Permite todas as origens localhost em desenvolvimento e Vercel em produção
                    String origin = request.getHeader("Origin");
                    if (origin != null) {
                        if (origin.startsWith("http://localhost") || origin.startsWith("http://127.0.0.1")) {
                            corsConfig.setAllowedOriginPatterns(java.util.List.of("http://localhost:*", "http://127.0.0.1:*"));
                        } else if (origin.contains("vercel.app")) {
                            // Permite qualquer subdomínio do Vercel dinamicamente
                            corsConfig.setAllowedOrigins(java.util.List.of(origin));
                        } else {
                            // Fallback para produção
                            corsConfig.setAllowedOrigins(java.util.List.of(
                                "https://frontend-zooracoes.vercel.app"
                            ));
                        }
                    } else {
                        // Fallback: permite todas as origens conhecidas
                        corsConfig.setAllowedOriginPatterns(java.util.List.of(
                            "http://localhost:*",
                            "http://127.0.0.1:*"
                        ));
                        corsConfig.setAllowedOrigins(java.util.List.of(
                            "https://frontend-zooracoes.vercel.app"
                        ));
                    }
                    corsConfig.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
                    corsConfig.setAllowedHeaders(java.util.List.of("*"));
                    corsConfig.setAllowCredentials(true);
                    corsConfig.setMaxAge(3600L); // Cache preflight por 1 hora
                    return corsConfig;
                }))
                .authorizeHttpRequests(auth -> {
                    // Endpoints públicos
                    auth.requestMatchers("/auth/login").permitAll();
                    auth.requestMatchers("/health").permitAll();
                    auth.requestMatchers("/version").permitAll();
                    auth.requestMatchers("/favicon.ico").permitAll();
                    // Todos os outros endpoints requerem autenticação
                    auth.anyRequest().authenticated();
                })
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedHandler(accessDeniedHandler())
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json;charset=UTF-8");
                            String json = String.format(
                                "{\"timestamp\":\"%s\",\"status\":401,\"error\":\"Não autenticado. Faça login para acessar este recurso.\",\"path\":\"%s\"}",
                                java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                                request.getRequestURI()
                            );
                            response.getWriter().write(json);
                        })
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
}
