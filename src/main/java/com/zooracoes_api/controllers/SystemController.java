package com.zooracoes_api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SystemController {

    @GetMapping("/health")
    public String health() {
        return "OK - ZoorAções API funcionando!";
    }

    @GetMapping("/version")
    public String version() {
        return "1.0.0";
    }
}
