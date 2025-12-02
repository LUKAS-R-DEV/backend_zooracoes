package com.zooracoes_api.config;

import com.zooracoes_api.entities.*;
import com.zooracoes_api.repositories.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class DataSeeder {

    @Value("${zooracoes.seed-enabled:false}")
    private boolean seedEnabled;

    @Bean
    CommandLineRunner initDatabase(
            UserRepository userRepository,
            TutorRepository tutorRepository,
            PetRepository petRepository,
            ServiceRepository serviceRepository,
            ScheduleRepository scheduleRepository,
            PasswordEncoder encoder
    ) {

        return args -> {
            if (!seedEnabled) {
                System.out.println("⚠ Seed desabilitado. Configure 'zooracoes.seed-enabled=true' para habilitar.");
                return;
            }

            // ---------------------------
            // USERS
            // ---------------------------
            if (userRepository.count() == 0) {
                UserEntity admin = new UserEntity();
                admin.setName("Administrador");
                admin.setEmail("admin@zooracoes.com");
                admin.setPassword(encoder.encode("123456"));
                admin.setRole(Role.ADMIN);

                userRepository.save(admin);
                System.out.println("Seed: Usuário admin criado.");
            }

            // ---------------------------
            // TUTORES
            // ---------------------------
            if (tutorRepository.count() == 0) {
                TutorEntity t1 = new TutorEntity();
                t1.setName("Carlos Silva");
                t1.setEmail("carlos@example.com");
                t1.setPhone("1199999-1111");
                t1.setAddress("Rua das Flores, 123");
                tutorRepository.save(t1);

                TutorEntity t2 = new TutorEntity();
                t2.setName("Maria Souza");
                t2.setEmail("maria@example.com");
                t2.setPhone("1199999-2222");
                t2.setAddress("Avenida Central, 456");
                tutorRepository.save(t2);

                System.out.println("Seed: Tutores criados.");
            }

            // ---------------------------
            // PETS
            // ---------------------------
            if (petRepository.count() == 0) {
                TutorEntity tutor1 = tutorRepository.findAll().get(0);
                TutorEntity tutor2 = tutorRepository.findAll().get(1);

                PetEntity p1 = new PetEntity();
                p1.setName("Rex");
                p1.setSpecies("Cachorro");
                p1.setBreed("Labrador");
                p1.setBirthDate(LocalDate.of(2020, 5, 10));
                p1.setWeight(32.5);
                p1.setTutor(tutor1);
                petRepository.save(p1);

                PetEntity p2 = new PetEntity();
                p2.setName("Mia");
                p2.setSpecies("Gato");
                p2.setBreed("Siamês");
                p2.setBirthDate(LocalDate.of(2022, 1, 15));
                p2.setWeight(4.8);
                p2.setTutor(tutor2);
                petRepository.save(p2);

                System.out.println("Seed: Pets criados.");
            }

            // ---------------------------
            // SERVICES
            // ---------------------------
            if (serviceRepository.count() == 0) {
                ServiceEntity s1 = new ServiceEntity();
                s1.setName("Banho");
                s1.setDescription("Banho completo no pet");
                s1.setPrice(50.0);
                s1.setDuration(30);
                serviceRepository.save(s1);

                ServiceEntity s2 = new ServiceEntity();
                s2.setName("Tosa");
                s2.setDescription("Tosa padrão completa");
                s2.setPrice(60.0);
                s2.setDuration(45);
                serviceRepository.save(s2);

                ServiceEntity s3 = new ServiceEntity();
                s3.setName("Consulta");
                s3.setDescription("Consulta veterinária geral");
                s3.setPrice(100.0);
                s3.setDuration(60);
                serviceRepository.save(s3);

                System.out.println("Seed: Serviços criados.");
            } else {
                // Atualizar serviços existentes que podem ter price ou duration como null
                List<ServiceEntity> servicesWithNulls = serviceRepository.findAll().stream()
                    .filter(s -> s.getPrice() == null || s.getDuration() == null)
                    .collect(java.util.stream.Collectors.toList());
                
                if (!servicesWithNulls.isEmpty()) {
                    for (ServiceEntity service : servicesWithNulls) {
                        if (service.getPrice() == null) {
                            service.setPrice(50.0);
                        }
                        if (service.getDuration() == null) {
                            service.setDuration(30);
                        }
                        serviceRepository.save(service);
                    }
                    System.out.println("Seed: " + servicesWithNulls.size() + " serviços atualizados com valores padrão.");
                }
            }

            // ---------------------------
            // SCHEDULES (opcional)
            // ---------------------------
            if (scheduleRepository.count() == 0) {
                TutorEntity tutor = tutorRepository.findAll().get(0);
                PetEntity pet = petRepository.findAll().get(0);
                ServiceEntity service = serviceRepository.findAll().get(0);

                ScheduleEntity sc = new ScheduleEntity();
                sc.setTutor(tutor);
                sc.setPet(pet);
                sc.setService(service);
                sc.setDateTime(LocalDateTime.now().plusDays(1));
                sc.setNotes("Primeiro agendamento de teste");
                scheduleRepository.save(sc);

                System.out.println("Seed: Agendamento criado.");
            }

            System.out.println("✔ SEED FINALIZADO!");
        };
    }
}


