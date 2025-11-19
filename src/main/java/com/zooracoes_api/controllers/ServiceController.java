package com.zooracoes_api.controllers;

import com.zooracoes_api.dtos.ServiceDTO;
import com.zooracoes_api.dtos.ServiceResponseDTO;
import com.zooracoes_api.services.ServiceService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ServiceController {

    private final ServiceService service;

    public ServiceController(ServiceService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ServiceResponseDTO create(@RequestBody ServiceDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<ServiceResponseDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ServiceResponseDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ServiceResponseDTO update(@PathVariable Long id, @RequestBody ServiceDTO dto) {
        return service.update(id, dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Serviço desativado com sucesso.";
    }
}
