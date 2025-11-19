package com.zooracoes_api.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "vaccines")
@Data
public class VaccineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private PetEntity pet;

    private String vaccineName;

    private LocalDate appliedDate;

    private LocalDate nextDoseDate;

    private String notes;

    private LocalDateTime createdAt = LocalDateTime.now();

    private boolean active = true;
}
