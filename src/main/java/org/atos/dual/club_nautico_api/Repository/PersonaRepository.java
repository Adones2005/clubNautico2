package org.atos.dual.club_nautico_api.Repository;

import jakarta.validation.constraints.NotBlank;
import org.atos.dual.club_nautico_api.Model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Optional<Persona> findByUsername(String username);

    boolean existsByUsername(@NotBlank String username);
}
