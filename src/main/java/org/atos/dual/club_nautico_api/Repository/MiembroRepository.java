package org.atos.dual.club_nautico_api.Repository;

import org.atos.dual.club_nautico_api.Model.Miembro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MiembroRepository extends JpaRepository<Miembro, Long> {

}