package com.zooracoes_api.services;

import com.zooracoes_api.dtos.PageResponseDTO;
import com.zooracoes_api.dtos.ScheduleDTO;
import com.zooracoes_api.dtos.ScheduleResponseDTO;
import com.zooracoes_api.entities.*;
import com.zooracoes_api.repositories.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final TutorRepository tutorRepository;
    private final PetRepository petRepository;
    private final ServiceRepository serviceRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, TutorRepository tutorRepository,
                           PetRepository petRepository, ServiceRepository serviceRepository) {

        this.scheduleRepository = scheduleRepository;
        this.tutorRepository = tutorRepository;
        this.petRepository = petRepository;
        this.serviceRepository = serviceRepository;
    }

    public ScheduleResponseDTO create(ScheduleDTO dto) {
        TutorEntity tutor = tutorRepository.findById(dto.tutorId())
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado"));

        PetEntity pet = petRepository.findById(dto.petId())
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));

        ServiceEntity service = serviceRepository.findById(dto.serviceId())
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        ScheduleEntity schedule = new ScheduleEntity();
        schedule.setTutor(tutor);
        schedule.setPet(pet);
        schedule.setService(service);
        schedule.setDateTime(dto.dateTime());
        schedule.setNotes(dto.notes());

        scheduleRepository.save(schedule);

        return toResponse(schedule);
    }

    public List<ScheduleResponseDTO> listAll() {
        return scheduleRepository.findAll()
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public PageResponseDTO<ScheduleResponseDTO> listAllPaginated(LocalDate date, Long serviceId, Pageable pageable) {
        Page<ScheduleEntity> page = scheduleRepository.findByFilters(date, serviceId, pageable);
        
        List<ScheduleResponseDTO> content = page.getContent()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        
        return PageResponseDTO.of(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public ScheduleResponseDTO findById(Long id) {
        ScheduleEntity schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        return toResponse(schedule);
    }

    public ScheduleResponseDTO update(Long id, ScheduleDTO dto) {
        ScheduleEntity schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        TutorEntity tutor = tutorRepository.findById(dto.tutorId())
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado"));

        PetEntity pet = petRepository.findById(dto.petId())
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));

        ServiceEntity service = serviceRepository.findById(dto.serviceId())
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        schedule.setTutor(tutor);
        schedule.setPet(pet);
        schedule.setService(service);
        schedule.setDateTime(dto.dateTime());
        schedule.setNotes(dto.notes());

        scheduleRepository.save(schedule);

        return toResponse(schedule);
    }

    public void cancel(Long id) {
        ScheduleEntity schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        schedule.setStatus(ScheduleStatus.CANCELLED);
        scheduleRepository.save(schedule);
    }

    private ScheduleResponseDTO toResponse(ScheduleEntity s) {
        return new ScheduleResponseDTO(
                s.getId(),
                s.getTutor().getId(),
                s.getPet().getId(),
                s.getService().getId(),
                s.getDateTime(),
                s.getNotes(),
                s.getStatus()
        );
    }
}
