package org.atos.dual.club_nautico_api.Repository;

import jakarta.validation.constraints.NotBlank;
import org.atos.dual.club_nautico_api.Model.Miembro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MiembroRepository extends JpaRepository<Miembro, Long> {

    boolean existsByPersonaId(Long id);
    Miembro findByPersonaId(Long id);

    boolean existsByPersonaUsername(@NotBlank String username);

    Miembro findByPersonaUsername(String miembro);
}