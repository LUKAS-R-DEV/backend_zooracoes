package com.zooracoes_api.controllers;

import com.zooracoes_api.dtos.PageResponseDTO;
import com.zooracoes_api.dtos.ScheduleDTO;
import com.zooracoes_api.dtos.ScheduleResponseDTO;
import com.zooracoes_api.services.ScheduleService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService service;

    public ScheduleController(ScheduleService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR')")
    @PostMapping
    public ScheduleResponseDTO create(@RequestBody ScheduleDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public PageResponseDTO<ScheduleResponseDTO> listAll(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) Long serviceId,
            @PageableDefault(page = 0, size = 10, sort = "dateTime") Pageable pageable) {
        return service.listAllPaginated(date, serviceId, pageable);
    }

    @GetMapping("/{id}")
    public ScheduleResponseDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR')")
    @PutMapping("/{id}")
    public ScheduleResponseDTO update(@PathVariable Long id, @RequestBody ScheduleDTO dto) {
        return service.update(id, dto);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR')")
    @PatchMapping("/{id}/status")
    public ScheduleResponseDTO updateStatus(@PathVariable Long id, @RequestBody com.zooracoes_api.dtos.StatusUpdateDTO dto) {
        return service.updateStatus(id, com.zooracoes_api.entities.ScheduleStatus.valueOf(dto.status()));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR')")
    @DeleteMapping("/{id}")
    public String cancel(@PathVariable Long id) {
        service.cancel(id);
        return "Agendamento cancelado com sucesso.";
    }
}
