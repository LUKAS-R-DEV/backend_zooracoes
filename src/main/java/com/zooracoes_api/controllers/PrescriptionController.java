package com.zooracoes_api.controllers;

import com.zooracoes_api.dtos.PrescriptionDTO;
import com.zooracoes_api.dtos.PrescriptionResponseDTO;
import com.zooracoes_api.services.PrescriptionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prescriptions")
public class PrescriptionController {

    private final PrescriptionService service;

    public PrescriptionController(PrescriptionService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR') or hasRole('FUNCIONARIO')")
    @PostMapping
    public PrescriptionResponseDTO create(@RequestBody PrescriptionDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<PrescriptionResponseDTO> listAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public PrescriptionResponseDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/pet/{petId}")
    public List<PrescriptionResponseDTO> listByPet(@PathVariable Long petId) {
        return service.listByPet(petId);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR') or hasRole('FUNCIONARIO')")
    @PutMapping("/{id}")
    public PrescriptionResponseDTO update(@PathVariable Long id, @RequestBody PrescriptionDTO dto) {
        return service.update(id, dto);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR') or hasRole('FUNCIONARIO')")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Prescrição removida com sucesso.";
    }
}
