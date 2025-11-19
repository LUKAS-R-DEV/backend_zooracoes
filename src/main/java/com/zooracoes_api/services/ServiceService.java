package com.zooracoes_api.services;

import com.zooracoes_api.dtos.ServiceDTO;
import com.zooracoes_api.dtos.ServiceResponseDTO;
import com.zooracoes_api.entities.ServiceEntity;
import com.zooracoes_api.repositories.ServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServiceService {

    private final ServiceRepository repository;

    public ServiceService(ServiceRepository repository) {
        this.repository = repository;
    }

    public ServiceResponseDTO create(ServiceDTO dto) {
        ServiceEntity service = new ServiceEntity();
        service.setName(dto.name());
        service.setDescription(dto.description());
        repository.save(service);

        return new ServiceResponseDTO(service.getId(), service.getName(), service.getDescription(), service.isActive());
    }

    public List<ServiceResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(s -> new ServiceResponseDTO(s.getId(), s.getName(), s.getDescription(), s.isActive()))
                .collect(Collectors.toList());
    }

    public ServiceResponseDTO findById(Long id) {
        ServiceEntity service = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        return new ServiceResponseDTO(service.getId(), service.getName(), service.getDescription(), service.isActive());
    }

    public ServiceResponseDTO update(Long id, ServiceDTO dto) {
        ServiceEntity service = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        service.setName(dto.name());
        service.setDescription(dto.description());

        repository.save(service);

        return new ServiceResponseDTO(service.getId(), service.getName(), service.getDescription(), service.isActive());
    }

    public void delete(Long id) {
        ServiceEntity service = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        service.setActive(false);
        repository.save(service);
    }
}
