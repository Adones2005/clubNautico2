package org.atos.dual.club_nautico_api.Config;

import org.atos.dual.club_nautico_api.Model.Persona;
import org.atos.dual.club_nautico_api.Model.Role;
import org.atos.dual.club_nautico_api.Repository.PersonaRepository;
import org.atos.dual.club_nautico_api.Repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(PersonaRepository personaRepository, RoleRepository roleRepository) {
        return args -> {
            // Verificar si los roles ya existen, si no, se insertan
            if (roleRepository.count() == 0) {
                // Crear los roles
                Role adminRole = new Role();
                adminRole.setName("ROLE_ADMIN");

                Role miembroRole = new Role();
                miembroRole.setName("ROLE_MIEMBRO");

                Role userRole = new Role();
                userRole.setName("ROLE_USER");

                // Guardar los roles en la base de datos
                roleRepository.save(adminRole);
                roleRepository.save(miembroRole);
                roleRepository.save(userRole);

                System.out.println("🟢 Roles por defecto creados.");
            } else {
                System.out.println("✅ Ya existen roles en la base de datos.");
            }

            // Verificar si las personas ya existen, si no, se insertan
            if (personaRepository.count() == 0) {
                // Crear y guardar la persona admin
                Persona admin = new Persona();
                admin.setUsername("admin");
                admin.setPassword("$2b$12$FVRijCavVZ7Qt15.CQssHe9m/6eLAdjAv0PiOKFIjMU161wApxzye"); // Contraseña codificada
                admin.setNombre("Admin");
                admin.setApellidos("User");
                admin.setRoles(Set.of(adminRole)); // Asignar el rol de admin

                // Crear y guardar la persona manager
                Persona manager = new Persona();
                manager.setUsername("manager");
                manager.setPassword("$2b$12$FVRijCavVZ7Qt15.CQssHe9m/6eLAdjAv0PiOKFIjMU161wApxzye"); // Contraseña codificada
                manager.setNombre("Manager");
                manager.setApellidos("User");
                manager.setRoles(Set.of(miembroRole)); // Asignar el rol de manager

                // Crear y guardar la persona normal
                Persona normal = new Persona();
                normal.setUsername("normal");
                normal.setPassword("$2b$12$FVRijCavVZ7Qt15.CQssHe9m/6eLAdjAv0PiOKFIjMU161wApxzye"); // Contraseña codificada
                normal.setNombre("Regular");
                normal.setApellidos("User");
                normal.setRoles(Set.of(userRole)); // Asignar el rol de usuario normal

                // Guardar las personas en la base de datos
                personaRepository.save(admin);
                personaRepository.save(manager);
                personaRepository.save(normal);

                System.out.println("🟢 Personas por defecto creadas.");
            } else {
                System.out.println("✅ Ya existen personas en la base de datos.");
            }
        };
    }
}
