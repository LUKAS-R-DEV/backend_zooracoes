package com.zooracoes_api.controllers;

import com.zooracoes_api.dtos.VaccineDTO;
import com.zooracoes_api.dtos.VaccineResponseDTO;
import com.zooracoes_api.services.VaccineService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vaccines")
public class VaccineController {

    private final VaccineService service;

    public VaccineController(VaccineService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR') or hasRole('FUNCIONARIO')")
    @PostMapping
    public VaccineResponseDTO create(@RequestBody VaccineDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<VaccineResponseDTO> listAll() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public VaccineResponseDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/pet/{petId}")
    public List<VaccineResponseDTO> listByPet(@PathVariable Long petId) {
        return service.listByPet(petId);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR') or hasRole('FUNCIONARIO')")
    @PutMapping("/{id}")
    public VaccineResponseDTO update(@PathVariable Long id, @RequestBody VaccineDTO dto) {
        return service.update(id, dto);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR') or hasRole('FUNCIONARIO')")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Registro de vacina removido com sucesso.";
    }
}
