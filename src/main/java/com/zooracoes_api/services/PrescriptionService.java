package com.zooracoes_api.services;

import com.zooracoes_api.dtos.PrescriptionDTO;
import com.zooracoes_api.dtos.PrescriptionResponseDTO;
import com.zooracoes_api.entities.PetEntity;
import com.zooracoes_api.entities.PrescriptionEntity;
import com.zooracoes_api.repositories.PetRepository;
import com.zooracoes_api.repositories.PrescriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final PetRepository petRepository;

    public PrescriptionService(PrescriptionRepository prescriptionRepository, PetRepository petRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.petRepository = petRepository;
    }

    public PrescriptionResponseDTO create(PrescriptionDTO dto) {

        PetEntity pet = petRepository.findById(dto.petId())
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));

        PrescriptionEntity p = new PrescriptionEntity();
        p.setPet(pet);
        p.setMedicationName(dto.medicationName());
        p.setDosage(dto.dosage());
        p.setInstructions(dto.instructions());

        prescriptionRepository.save(p);

        return toResponse(p);
    }

    public List<PrescriptionResponseDTO> findAll() {
        return prescriptionRepository.findByActiveTrue(org.springframework.data.domain.Pageable.unpaged())
                .getContent()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public PrescriptionResponseDTO findById(Long id) {
        PrescriptionEntity p = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescrição não encontrada"));
        
        if (!p.isActive()) {
            throw new RuntimeException("Prescrição não encontrada");
        }
        
        return toResponse(p);
    }

    public List<PrescriptionResponseDTO> listByPet(Long petId) {
        return prescriptionRepository.findByPetIdAndActiveTrue(petId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public PrescriptionResponseDTO update(Long id, PrescriptionDTO dto) {
        PrescriptionEntity p = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescrição não encontrada"));
        
        if (!p.isActive()) {
            throw new RuntimeException("Prescrição não encontrada");
        }

        PetEntity pet = petRepository.findById(dto.petId())
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));

        p.setPet(pet);
        p.setMedicationName(dto.medicationName());
        p.setDosage(dto.dosage());
        p.setInstructions(dto.instructions());

        prescriptionRepository.save(p);

        return toResponse(p);
    }

    public void delete(Long id) {
        PrescriptionEntity p = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescrição não encontrada"));
        
        if (!p.isActive()) {
            throw new RuntimeException("Prescrição não encontrada");
        }

        p.setActive(false);
        prescriptionRepository.save(p);
    }

    private PrescriptionResponseDTO toResponse(PrescriptionEntity p) {
        return new PrescriptionResponseDTO(
                p.getId(),
                p.getPet().getId(),
                p.getMedicationName(),
                p.getDosage(),
                p.getInstructions(),
                p.getPrescribedAt()
        );
    }
}
