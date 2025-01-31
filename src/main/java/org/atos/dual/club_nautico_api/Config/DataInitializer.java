package org.atos.dual.club_nautico_api.Config;

import org.atos.dual.club_nautico_api.Model.*;
import org.atos.dual.club_nautico_api.DTO.*;
import org.atos.dual.club_nautico_api.Repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(PersonaRepository personaRepository,
                                   RoleRepository roleRepository,
                                   BarcoRepository barcoRepository,
                                   ViajeRepository viajeRepository,
                                   MiembroRepository miembroRepository) {
        return args -> {
            // Verificar si los roles ya existen, si no, se insertan
            if (roleRepository.count() == 0) {
                Role adminRole = new Role();
                adminRole.setName("ROLE_ADMIN");

                Role miembroRole = new Role();
                miembroRole.setName("ROLE_MIEMBRO");

                Role userRole = new Role();
                userRole.setName("ROLE_USER");

                roleRepository.save(adminRole);
                roleRepository.save(miembroRole);
                roleRepository.save(userRole);

                System.out.println("🟢 Roles por defecto creados.");
            } else {
                System.out.println("✅ Ya existen roles en la base de datos.");
            }

            // Crear y guardar las personas si no existen
            if (personaRepository.count() == 0) {
                // Obtener roles
                Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow();
                Role miembroRole = roleRepository.findByName("ROLE_MIEMBRO").orElseThrow();
                Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow();

                // Crear personas
                Persona admin = new Persona();
                admin.setUsername("admin");
                admin.setPassword("$2b$12$FVRijCavVZ7Qt15.CQssHe9m/6eLAdjAv0PiOKFIjMU161wApxzye");
                admin.setNombre("Admin");
                admin.setApellidos("User");
                Set<Role> roles1 = new HashSet<>();
                roles1.add(adminRole);
                admin.setRoles(roles1);

                Persona miembro = new Persona();
                miembro.setUsername("miembro");
                miembro.setPassword("$2b$12$FVRijCavVZ7Qt15.CQssHe9m/6eLAdjAv0PiOKFIjMU161wApxzye");
                miembro.setNombre("Miembro");
                miembro.setApellidos("User");
                Set<Role> roles2 = new HashSet<>();
                roles2.add(miembroRole);
                miembro.setRoles(roles2);

                Persona normal = new Persona();
                normal.setUsername("normal");
                normal.setPassword("$2b$12$FVRijCavVZ7Qt15.CQssHe9m/6eLAdjAv0PiOKFIjMU161wApxzye");
                normal.setNombre("Regular");
                normal.setApellidos("User");
                Set<Role> roles3 = new HashSet<>();
                roles3.add(userRole);
                normal.setRoles(roles3);

                // Guardar personas
                personaRepository.save(admin);
                personaRepository.save(miembro);
                personaRepository.save(normal);

                System.out.println("🟢 Personas por defecto creadas.");
            } else {
                System.out.println("✅ Ya existen personas en la base de datos.");
            }

            // Crear y guardar los miembros si no existen
            if (miembroRepository.count() == 0) {
                // Crear Miembro
                Persona personaMiembro = personaRepository.findByUsername("miembro").orElseThrow();
                Miembro miembro = new Miembro();
                miembro.setPersona(personaMiembro);
                miembro.setBarcos(Collections.emptyList()); // Si es necesario, puedes asignar barcos aquí

                // Guardar miembro
                miembroRepository.save(miembro);

                System.out.println("🟢 Miembro por defecto creado.");
            } else {
                System.out.println("✅ Ya existen miembros en la base de datos.");
            }

            // Crear y guardar los barcos si no existen
            if (barcoRepository.count() == 0) {
                // Crear barcos
                Barco barco1 = new Barco();
                barco1.setNombre("Barco 1");
                barco1.setMatricula("123ABC");
                barco1.setAmarre(1);
                barco1.setTarifa(500.0);

                Barco barco2 = new Barco();
                barco2.setNombre("Barco 2");
                barco2.setMatricula("456DEF");
                barco2.setAmarre(2);
                barco2.setTarifa(600.0);

                // Asignar un propietario a los barcos
                Miembro miembro = miembroRepository.findByPersonaUsername("miembro");
                barco1.setPropietario(miembro);
                barco2.setPropietario(miembro);

                barcoRepository.save(barco1);
                barcoRepository.save(barco2);

                System.out.println("🟢 Barcos por defecto creados.");
            } else {
                System.out.println("✅ Ya existen barcos en la base de datos.");
            }

            // Crear y guardar los viajes si no existen
            if (viajeRepository.count() == 0) {
                // Crear un viaje
                Viaje viaje1 = new Viaje();
                viaje1.setDescripcion("Viaje a Isla");
                viaje1.setFechaHora(LocalDateTime.of(2025, 6, 1, 10, 0));
                viaje1.setBarco(barcoRepository.findByNombre("Barco 1"));
                viaje1.setOrganizador(personaRepository.findByUsername("admin").orElseThrow());

                Viaje viaje2 = new Viaje();
                viaje2.setDescripcion("Viaje a Puerto");
                viaje2.setFechaHora(LocalDateTime.of(2025, 7, 1, 12, 0));
                viaje2.setBarco(barcoRepository.findByNombre("Barco 2"));
                viaje2.setOrganizador(personaRepository.findByUsername("miembro").orElseThrow());

                // Guardar viajes
                viajeRepository.save(viaje1);
                viajeRepository.save(viaje2);

                System.out.println("🟢 Viajes por defecto creados.");
            } else {
                System.out.println("✅ Ya existen viajes en la base de datos.");
            }
        };
    }
}
