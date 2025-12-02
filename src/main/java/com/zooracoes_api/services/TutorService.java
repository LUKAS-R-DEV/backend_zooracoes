package com.zooracoes_api.services;

import com.zooracoes_api.dtos.PageResponseDTO;
import com.zooracoes_api.dtos.TutorDTO;
import com.zooracoes_api.dtos.TutorResponseDTO;
import com.zooracoes_api.entities.TutorEntity;
import com.zooracoes_api.repositories.TutorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TutorService {

    private final TutorRepository repository;

    public TutorService(TutorRepository repository) {
        this.repository = repository;
    }

    public TutorResponseDTO create(TutorDTO dto) {
        TutorEntity tutor = new TutorEntity();
        tutor.setName(dto.name());
        tutor.setEmail(dto.email());
        tutor.setPhone(dto.phone());
        tutor.setAddress(dto.address());

        repository.save(tutor);

        return new TutorResponseDTO(
                tutor.getId(),
                tutor.getName(),
                tutor.getEmail(),
                tutor.getPhone(),
                tutor.getAddress()
        );
    }

    public List<TutorResponseDTO> findAll() {
        return repository.findByActiveTrue(org.springframework.data.domain.Pageable.unpaged())
                .getContent()
                .stream()
                .map(t -> new TutorResponseDTO(
                        t.getId(), t.getName(), t.getEmail(), t.getPhone(), t.getAddress()
                ))
                .collect(Collectors.toList());
    }

    public PageResponseDTO<TutorResponseDTO> findAllPaginated(Pageable pageable) {
        // Filtra apenas tutores ativos
        Page<TutorEntity> page = repository.findByActiveTrue(pageable);
        
        List<TutorResponseDTO> content = page.getContent()
                .stream()
                .map(t -> new TutorResponseDTO(
                        t.getId(), t.getName(), t.getEmail(), t.getPhone(), t.getAddress()
                ))
                .collect(Collectors.toList());
        
        return PageResponseDTO.of(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public TutorResponseDTO findById(Long id) {
        TutorEntity tutor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado"));
        
        if (!tutor.isActive()) {
            throw new RuntimeException("Tutor não encontrado");
        }

        return new TutorResponseDTO(
                tutor.getId(),
                tutor.getName(),
                tutor.getEmail(),
                tutor.getPhone(),
                tutor.getAddress()
        );
    }

    public TutorResponseDTO update(Long id, TutorDTO dto) {
        TutorEntity tutor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado"));
        
        if (!tutor.isActive()) {
            throw new RuntimeException("Tutor não encontrado");
        }

        tutor.setName(dto.name());
        tutor.setEmail(dto.email());
        tutor.setPhone(dto.phone());
        tutor.setAddress(dto.address());

        repository.save(tutor);

        return new TutorResponseDTO(
                tutor.getId(),
                tutor.getName(),
                tutor.getEmail(),
                tutor.getPhone(),
                tutor.getAddress()
        );
    }

    public void delete(Long id) {
        TutorEntity tutor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado"));
        
        if (!tutor.isActive()) {
            throw new RuntimeException("Tutor não encontrado");
        }

        tutor.setActive(false);
        repository.save(tutor);
    }
}
