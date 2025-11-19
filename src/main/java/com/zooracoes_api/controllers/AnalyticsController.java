package com.zooracoes_api.controllers;

import com.zooracoes_api.dtos.AnalyticsResponseDTO;
import com.zooracoes_api.services.AnalyticsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    private final AnalyticsService service;

    public AnalyticsController(AnalyticsService service) {
        this.service = service;
    }

    @GetMapping("/dashboard")
    public AnalyticsResponseDTO getDashboard() {
        return service.getDashboardData();
    }
}
