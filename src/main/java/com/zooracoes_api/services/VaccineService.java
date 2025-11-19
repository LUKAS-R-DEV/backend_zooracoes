package com.zooracoes_api.services;

import com.zooracoes_api.dtos.VaccineDTO;
import com.zooracoes_api.dtos.VaccineResponseDTO;
import com.zooracoes_api.entities.PetEntity;
import com.zooracoes_api.entities.VaccineEntity;
import com.zooracoes_api.repositories.PetRepository;
import com.zooracoes_api.repositories.VaccineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VaccineService {

    private final VaccineRepository vaccineRepository;
    private final PetRepository petRepository;

    public VaccineService(VaccineRepository vaccineRepository, PetRepository petRepository) {
        this.vaccineRepository = vaccineRepository;
        this.petRepository = petRepository;
    }

    public VaccineResponseDTO create(VaccineDTO dto) {
        PetEntity pet = petRepository.findById(dto.petId())
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));

        VaccineEntity v = new VaccineEntity();
        v.setPet(pet);
        v.setVaccineName(dto.vaccineName());
        v.setAppliedDate(dto.appliedDate());
        v.setNextDoseDate(dto.nextDoseDate());
        v.setNotes(dto.notes());

        vaccineRepository.save(v);

        return toResponse(v);
    }

    public List<VaccineResponseDTO> listAll() {
        return vaccineRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public VaccineResponseDTO findById(Long id) {
        VaccineEntity v = vaccineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro de vacina não encontrado"));
        return toResponse(v);
    }

    public List<VaccineResponseDTO> listByPet(Long petId) {
        return vaccineRepository.findByPetId(petId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public VaccineResponseDTO update(Long id, VaccineDTO dto) {
        VaccineEntity v = vaccineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vacina não encontrada"));

        PetEntity pet = petRepository.findById(dto.petId())
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));

        v.setPet(pet);
        v.setVaccineName(dto.vaccineName());
        v.setAppliedDate(dto.appliedDate());
        v.setNextDoseDate(dto.nextDoseDate());
        v.setNotes(dto.notes());

        vaccineRepository.save(v);

        return toResponse(v);
    }

    public void delete(Long id) {
        VaccineEntity v = vaccineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vacina não encontrada"));

        v.setActive(false);

        vaccineRepository.save(v);
    }

    private VaccineResponseDTO toResponse(VaccineEntity v) {
        return new VaccineResponseDTO(
                v.getId(),
                v.getPet().getId(),
                v.getVaccineName(),
                v.getAppliedDate(),
                v.getNextDoseDate(),
                v.getNotes()
        );
    }
}
