package org.atos.dual.club_nautico_api.Config;

import org.atos.dual.club_nautico_api.Model.Persona;
import org.atos.dual.club_nautico_api.Model.Role;
import org.atos.dual.club_nautico_api.Repository.PersonaRepository;
import org.atos.dual.club_nautico_api.Repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(PersonaRepository personaRepository, RoleRepository roleRepository) {
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

            // Verificar si las personas ya existen, si no, se insertan
            if (personaRepository.count() == 0) {
                // Obtener los roles de la base de datos
                Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow();
                Role miembroRole = roleRepository.findByName("ROLE_MIEMBRO").orElseThrow();
                Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow();


                // Crear y guardar la persona admin
                Persona admin = new Persona();
                admin.setUsername("admin");
                admin.setPassword("$2b$12$FVRijCavVZ7Qt15.CQssHe9m/6eLAdjAv0PiOKFIjMU161wApxzye");
                admin.setNombre("Admin");
                admin.setApellidos("User");
                Set<Role> roles1 = new HashSet<Role>();
                roles1.add(adminRole);
                admin.setRoles(roles1);

                // Crear y guardar la persona manager
                Persona miembro = new Persona();
                miembro.setUsername("miembro");
                miembro.setPassword("$2b$12$FVRijCavVZ7Qt15.CQssHe9m/6eLAdjAv0PiOKFIjMU161wApxzye");
                miembro.setNombre("miembro");
                miembro.setApellidos("User");
                Set<Role> roles2 = new HashSet<>();
                roles2.add(miembroRole);
                admin.setRoles(roles2);

                // Crear y guardar la persona normal
                Persona normal = new Persona();
                normal.setUsername("normal");
                normal.setPassword("$2b$12$FVRijCavVZ7Qt15.CQssHe9m/6eLAdjAv0PiOKFIjMU161wApxzye");
                normal.setNombre("Regular");
                normal.setApellidos("User");
                Set<Role> roles3 = new HashSet<>();
                roles3.add(userRole);
                admin.setRoles(roles3);

                // Guardar las personas en la base de datos
                personaRepository.save(admin);
                personaRepository.save(miembro);
                personaRepository.save(normal);

                System.out.println("🟢 Personas por defecto creadas.");
            } else {
                System.out.println("✅ Ya existen personas en la base de datos.");
            }
        };
    }
}
