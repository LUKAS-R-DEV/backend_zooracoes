package com.zooracoes_api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                     AccessDeniedException accessDeniedException) throws IOException, ServletException {
        
        logger.error("=== ACCESS DENIED HANDLER CALLED ===");
        logger.warn("Access denied for path: {} - Message: {}", request.getRequestURI(), accessDeniedException.getMessage());
        
        try {
            // Garantir que o response não foi commitado
            if (response.isCommitted()) {
                logger.warn("Response already committed, cannot write error response");
                return;
            }
            
            // Limpar qualquer conteúdo anterior
            response.reset();
            response.resetBuffer();
            
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");
            
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            String errorMessage = "Acesso negado. Você não tem permissão para realizar esta ação.";
            String path = request.getRequestURI();
            
            // Escrever JSON diretamente para garantir que funcione
            String jsonResponse = String.format(
                "{\"timestamp\":\"%s\",\"status\":403,\"error\":\"%s\",\"path\":\"%s\"}",
                timestamp, errorMessage, path
            );
            
            logger.info("Writing JSON response: {}", jsonResponse);
            
            PrintWriter writer = response.getWriter();
            writer.write(jsonResponse);
            writer.flush();
            
            logger.info("Response written successfully");
        } catch (Exception e) {
            logger.error("Error in AccessDeniedHandler", e);
            // Último recurso: tentar escrever uma resposta simples
            try {
                response.getWriter().write("{\"error\":\"Acesso negado\"}");
            } catch (Exception ex) {
                logger.error("Failed to write fallback response", ex);
            }
        }
    }
}

