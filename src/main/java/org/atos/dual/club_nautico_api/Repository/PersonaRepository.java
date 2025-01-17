package org.atos.dual.club_nautico_api.Repository;

import org.atos.dual.club_nautico_api.Model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    boolean existsByDni(String DNI);
    Persona findByDni(String DNI);
}
