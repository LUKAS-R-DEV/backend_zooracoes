package com.zooracoes_api.services;

import com.zooracoes_api.dtos.AnalyticsResponseDTO;
import com.zooracoes_api.entities.ScheduleEntity;
import com.zooracoes_api.entities.VaccineEntity;
import com.zooracoes_api.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class AnalyticsService {

    private final TutorRepository tutorRepository;
    private final PetRepository petRepository;
    private final ServiceRepository serviceRepository;
    private final ScheduleRepository scheduleRepository;
    private final VaccineRepository vaccineRepository;

    public AnalyticsService(
            TutorRepository tutorRepository,
            PetRepository petRepository,
            ServiceRepository serviceRepository,
            ScheduleRepository scheduleRepository,
            VaccineRepository vaccineRepository
    ) {
        this.tutorRepository = tutorRepository;
        this.petRepository = petRepository;
        this.serviceRepository = serviceRepository;
        this.scheduleRepository = scheduleRepository;
        this.vaccineRepository = vaccineRepository;
    }

    public AnalyticsResponseDTO getDashboardData() {
        // Conta apenas registros ativos
        long totalTutors = tutorRepository.findByActiveTrue(org.springframework.data.domain.Pageable.unpaged()).getTotalElements();
        long totalPets = petRepository.findByActiveTrue(org.springframework.data.domain.Pageable.unpaged()).getTotalElements();
        long totalServices = (long) serviceRepository.findByActiveTrue().size();
        long totalSchedules = scheduleRepository.findByActiveTrue(org.springframework.data.domain.Pageable.unpaged()).getTotalElements();

        // Agendamentos de hoje (apenas ativos)
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();

        long schedulesToday = scheduleRepository
                .findByDateTimeBetweenAndActiveTrue(start, end)
                .size();

        // Vacinas atrasadas (apenas ativas)
        long vaccinesLate = vaccineRepository.findByActiveTrue(org.springframework.data.domain.Pageable.unpaged())
                .getContent()
                .stream()
                .filter(v -> v.getNextDoseDate() != null)
                .filter(v -> v.getNextDoseDate().isBefore(LocalDate.now()))
                .count();

        // Vacinas próximas 7 dias (apenas ativas)
        LocalDate nextWeek = LocalDate.now().plusDays(7);

        long vaccinesNext7Days = vaccineRepository.findByActiveTrue(org.springframework.data.domain.Pageable.unpaged())
                .getContent()
                .stream()
                .filter(v -> v.getNextDoseDate() != null)
                .filter(v -> !v.getNextDoseDate().isBefore(LocalDate.now())
                        && v.getNextDoseDate().isBefore(nextWeek))
                .count();

        // Pets por espécie (apenas ativos)
        Map<String, Long> petsBySpecies = petRepository.findByActiveTrue(org.springframework.data.domain.Pageable.unpaged())
                .getContent()
                .stream()
                .filter(p -> p != null && p.getSpecies() != null)
                .collect(Collectors.groupingBy(
                        p -> p.getSpecies(),
                        Collectors.counting()
                ));

        // Agendamentos por tipo de serviço (apenas ativos)
        Map<String, Long> schedulesByService = scheduleRepository.findByActiveTrue(org.springframework.data.domain.Pageable.unpaged())
                .getContent()
                .stream()
                .filter(s -> s != null && s.getService() != null && s.getService().getName() != null)
                .collect(Collectors.groupingBy(
                        s -> s.getService().getName(),
                        Collectors.counting()
                ));

        return new AnalyticsResponseDTO(
                totalTutors,
                totalPets,
                totalServices,
                totalSchedules,
                schedulesToday,
                vaccinesLate,
                vaccinesNext7Days,
                petsBySpecies,
                schedulesByService
        );
    }
}
