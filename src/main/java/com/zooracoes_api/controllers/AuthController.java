package com.zooracoes_api.controllers;

import com.zooracoes_api.dtos.AuthResponseDTO;
import com.zooracoes_api.dtos.LoginDTO;
import com.zooracoes_api.dtos.RegisterDTO;
import com.zooracoes_api.dtos.UserResponseDTO;
import com.zooracoes_api.entities.UserEntity;
import com.zooracoes_api.security.TokenService;
import com.zooracoes_api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder encoder;
    private final TokenService tokenService;

    public AuthController(UserService userService, PasswordEncoder encoder, TokenService tokenService) {
        this.userService = userService;
        this.encoder = encoder;
        this.tokenService = tokenService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDTO dto) {
        // Verifica email em qualquer status (ativo ou inativo) para evitar duplicação
        if (userService.findByEmailAnyStatus(dto.email()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }
        userService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso.");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginDTO dto) {
        UserEntity user = userService.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!encoder.matches(dto.password(), user.getPassword())) {
            throw new RuntimeException("Senha inválida");
        }

        String token = tokenService.generateToken(user);
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> me(Authentication authentication) {
        if (authentication == null) {
            throw new RuntimeException("Não autenticado");
        }

        String email = authentication.getName();
        UserEntity user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        UserResponseDTO response = new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.isActive(),
                user.getCreatedAt()
        );
        
        return ResponseEntity.ok(response);
    }
}
