package com.zooracoes_api.controllers;

import com.zooracoes_api.dtos.PageResponseDTO;
import com.zooracoes_api.dtos.PetDTO;
import com.zooracoes_api.dtos.PetResponseDTO;
import com.zooracoes_api.services.PetService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService service;

    public PetController(PetService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR')")
    @PostMapping
    public PetResponseDTO create(@RequestBody PetDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public PageResponseDTO<PetResponseDTO> list(
            @RequestParam(required = false) String species,
            @RequestParam(required = false) Long tutorId,
            @PageableDefault(page = 0, size = 10, sort = "name") Pageable pageable) {
        return service.listAllPaginated(species, tutorId, pageable);
    }

    @GetMapping("/{id}")
    public PetResponseDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/tutor/{tutorId}")
    public List<PetResponseDTO> listByTutor(@PathVariable Long tutorId) {
        return service.listByTutor(tutorId);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR')")
    @PutMapping("/{id}")
    public PetResponseDTO update(@PathVariable Long id, @RequestBody PetDTO dto) {
        return service.update(id, dto);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR')")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Pet desativado com sucesso.";
    }
}
