package com.zooracoes_api.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "prescriptions")
@Data
public class PrescriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private PetEntity pet;

    private String medicationName;

    private String dosage;

    private String instructions;

    private LocalDate startDate;

    private LocalDate endDate;

    private String veterinarian;

    private LocalDateTime prescribedAt = LocalDateTime.now();

    private LocalDateTime createdAt = LocalDateTime.now();

    private boolean active = true;
}
