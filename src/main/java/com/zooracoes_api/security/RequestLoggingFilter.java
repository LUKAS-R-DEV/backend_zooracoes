package com.zooracoes_api.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Order(1)
public class RequestLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String uri = httpRequest.getRequestURI();

        long startTime = System.currentTimeMillis();
        String method = httpRequest.getMethod();
        String queryString = httpRequest.getQueryString();
        String fullPath = queryString == null ? uri : uri + "?" + queryString;
        String clientIp = getClientIpAddress(httpRequest);
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication != null && authentication.isAuthenticated() 
                && !authentication.getName().equals("anonymousUser") 
                ? authentication.getName() 
                : "N/A";

        // Log BEGIN
        logger.info("═══════════════════════════════════════════════════════════════");
        logger.info("BEGIN REQUEST - {} {} | IP: {} | User: {}", method, fullPath, clientIp, userEmail);
        logger.info("═══════════════════════════════════════════════════════════════");

        try {
            chain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            int status = httpResponse.getStatus();
            
            // Log END
            logger.info("═══════════════════════════════════════════════════════════════");
            logger.info("END REQUEST - {} {} | Status: {} | Duration: {}ms | IP: {} | User: {}", 
                    method, fullPath, status, duration, clientIp, userEmail);
            logger.info("═══════════════════════════════════════════════════════════════");
        }
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("RequestLoggingFilter initialized");
    }

    @Override
    public void destroy() {
        logger.info("RequestLoggingFilter destroyed");
    }
}


