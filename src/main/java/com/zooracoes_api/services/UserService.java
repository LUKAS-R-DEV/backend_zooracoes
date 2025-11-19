package com.zooracoes_api.services;

import com.zooracoes_api.dtos.RegisterDTO;
import com.zooracoes_api.entities.UserEntity;
import com.zooracoes_api.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        return repository.findByEmail(email);
    }
}
