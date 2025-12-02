package com.zooracoes_api.services;

import com.zooracoes_api.dtos.PageResponseDTO;
import com.zooracoes_api.dtos.RegisterDTO;
import com.zooracoes_api.dtos.UserDTO;
import com.zooracoes_api.dtos.UserResponseDTO;
import com.zooracoes_api.entities.UserEntity;
import com.zooracoes_api.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public UserEntity create(RegisterDTO dto) {
        UserEntity user = new UserEntity();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(encoder.encode(dto.password()));
        user.setRole(dto.role());
        return repository.save(user);
    }

    public Optional<UserEntity> findByEmail(String email) {
        return repository.findByEmailAndActiveTrue(email);
    }
    
    public Optional<UserEntity> findByEmailAnyStatus(String email) {
        return repository.findByEmail(email);
    }

    public PageResponseDTO<UserResponseDTO> findAllPaginated(Pageable pageable) {
        Page<UserEntity> page = repository.findByActiveTrue(pageable);
        
        List<UserResponseDTO> content = page.getContent()
                .stream()
                .map(u -> new UserResponseDTO(
                        u.getId(),
                        u.getName(),
                        u.getEmail(),
                        u.getRole(),
                        u.isActive(),
                        u.getCreatedAt()
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

    public UserResponseDTO findById(Long id) {
        UserEntity user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        if (!user.isActive()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.isActive(),
                user.getCreatedAt()
        );
    }

    public UserResponseDTO update(Long id, UserDTO dto) {
        UserEntity user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        if (!user.isActive()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        user.setName(dto.name());
        user.setEmail(dto.email());
        if (dto.password() != null && !dto.password().isEmpty()) {
            user.setPassword(encoder.encode(dto.password()));
        }
        user.setRole(dto.role());

        repository.save(user);

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.isActive(),
                user.getCreatedAt()
        );
    }

    public void delete(Long id) {
        UserEntity user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        if (!user.isActive()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        user.setActive(false);
        repository.save(user);
    }
}
