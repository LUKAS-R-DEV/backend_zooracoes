package com.zooracoes_api.services;

import com.zooracoes_api.dtos.PageResponseDTO;
import com.zooracoes_api.dtos.PetDTO;
import com.zooracoes_api.dtos.PetResponseDTO;
import com.zooracoes_api.entities.PetEntity;
import com.zooracoes_api.entities.TutorEntity;
import com.zooracoes_api.repositories.PetRepository;
import com.zooracoes_api.repositories.TutorRepository;
import com.zooracoes_api.repositories.ScheduleRepository;
import com.zooracoes_api.repositories.VaccineRepository;
import com.zooracoes_api.repositories.PrescriptionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PetService {

    private final PetRepository petRepository;
    private final TutorRepository tutorRepository;
    private final ScheduleRepository scheduleRepository;
    private final VaccineRepository vaccineRepository;
    private final PrescriptionRepository prescriptionRepository;

    public PetService(PetRepository petRepository, 
                     TutorRepository tutorRepository,
                     ScheduleRepository scheduleRepository,
                     VaccineRepository vaccineRepository,
                     PrescriptionRepository prescriptionRepository) {
        this.petRepository = petRepository;
        this.tutorRepository = tutorRepository;
        this.scheduleRepository = scheduleRepository;
        this.vaccineRepository = vaccineRepository;
        this.prescriptionRepository = prescriptionRepository;
    }

    public PetResponseDTO create(PetDTO dto) {
        TutorEntity tutor = tutorRepository.findById(dto.tutorId())
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado"));

        PetEntity pet = new PetEntity();
        pet.setName(dto.name());
        pet.setSpecies(dto.species());
        pet.setBreed(dto.breed());
        pet.setWeight(dto.weight());
        pet.setBirthDate(dto.birthDate());
        pet.setTutor(tutor);

        petRepository.save(pet);

        return new PetResponseDTO(
                pet.getId(),
                pet.getName(),
                pet.getSpecies(),
                pet.getBreed(),
                pet.getWeight(),
                pet.getBirthDate(),
                tutor.getId(),
                tutor.getName()
                
        );
    }

    public List<PetResponseDTO> listAll() {
        return petRepository.findByActiveTrue(org.springframework.data.domain.Pageable.unpaged())
                .getContent()
                .stream()
                .map(p -> new PetResponseDTO(
                        p.getId(), p.getName(), p.getSpecies(), p.getBreed(),
                        p.getWeight(), p.getBirthDate(), p.getTutor().getId(), p.getTutor().getName()))
                .collect(Collectors.toList());
    }

    public PageResponseDTO<PetResponseDTO> listAllPaginated(String species, Long tutorId, Pageable pageable) {
        Page<PetEntity> page = petRepository.findByFilters(species, tutorId, pageable);
        
        List<PetResponseDTO> content = page.getContent()
                .stream()
                .map(p -> new PetResponseDTO(
                        p.getId(), p.getName(), p.getSpecies(), p.getBreed(),
                        p.getWeight(), p.getBirthDate(), p.getTutor().getId(), p.getTutor().getName()))
                .collect(Collectors.toList());
        
        return PageResponseDTO.of(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public PetResponseDTO findById(Long id) {
        PetEntity pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));
        
        if (!pet.isActive()) {
            throw new RuntimeException("Pet não encontrado");
        }

        return new PetResponseDTO(
                pet.getId(),
                pet.getName(),
                pet.getSpecies(),
                pet.getBreed(),
                pet.getWeight(),
                pet.getBirthDate(),
                pet.getTutor().getId(),
                pet.getTutor().getName()
        );
    }

    public List<PetResponseDTO> listByTutor(Long tutorId) {
        return petRepository.findByTutor_IdAndActiveTrue(tutorId)
                .stream()
                .map(p -> new PetResponseDTO(
                        p.getId(), p.getName(), p.getSpecies(), p.getBreed(),
                        p.getWeight(), p.getBirthDate(), p.getTutor().getId(), p.getTutor().getName()))
                .collect(Collectors.toList());
    }

    public PetResponseDTO update(Long id, PetDTO dto) {
        PetEntity pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));
        
        if (!pet.isActive()) {
            throw new RuntimeException("Pet não encontrado");
        }

        TutorEntity tutor = tutorRepository.findById(dto.tutorId())
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado"));

        pet.setName(dto.name());
        pet.setSpecies(dto.species());
        pet.setBreed(dto.breed());
        pet.setWeight(dto.weight());
        pet.setBirthDate(dto.birthDate());
        pet.setTutor(tutor);

        petRepository.save(pet);

        return new PetResponseDTO(
                pet.getId(), pet.getName(), pet.getSpecies(), pet.getBreed(),
                pet.getWeight(), pet.getBirthDate(), tutor.getId(), tutor.getName()
        );
    }

    public void delete(Long id) {
        PetEntity pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));
        
        if (!pet.isActive()) {
            throw new RuntimeException("Pet não encontrado");
        }

        // Desativar agendamentos relacionados
        scheduleRepository.findByPetIdAndActiveTrue(id)
                .forEach(schedule -> {
                    schedule.setActive(false);
                    scheduleRepository.save(schedule);
                });

        // Desativar vacinas relacionadas
        vaccineRepository.findByPetIdAndActiveTrue(id)
                .forEach(vaccine -> {
                    vaccine.setActive(false);
                    vaccineRepository.save(vaccine);
                });

        // Desativar prescrições relacionadas
        prescriptionRepository.findByPetIdAndActiveTrue(id)
                .forEach(prescription -> {
                    prescription.setActive(false);
                    prescriptionRepository.save(prescription);
                });

        // Desativar o pet
        pet.setActive(false);
        petRepository.save(pet);
    }
}
