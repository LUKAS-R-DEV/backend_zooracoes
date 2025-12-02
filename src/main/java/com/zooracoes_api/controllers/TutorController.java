package com.zooracoes_api.controllers;

import com.zooracoes_api.dtos.PageResponseDTO;
import com.zooracoes_api.dtos.TutorDTO;
import com.zooracoes_api.dtos.TutorResponseDTO;
import com.zooracoes_api.services.TutorService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tutors")
public class TutorController {

    private final TutorService service;

    public TutorController(TutorService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR')")
    @PostMapping
    public TutorResponseDTO create(@RequestBody TutorDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public PageResponseDTO<TutorResponseDTO> findAll(
            @PageableDefault(page = 0, size = 10, sort = "name") Pageable pageable) {
        return service.findAllPaginated(pageable);
    }

    @GetMapping("/{id}")
    public TutorResponseDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR')")
    @PutMapping("/{id}")
    public TutorResponseDTO update(@PathVariable Long id, @RequestBody TutorDTO dto) {
        return service.update(id, dto);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Tutor desativado com sucesso");
    }
}
