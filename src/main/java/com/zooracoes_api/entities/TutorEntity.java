package com.zooracoes_api.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tutors")
@Data
public class TutorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;

    private String address;

    private boolean active = true;
}
