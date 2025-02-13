package org.atos.dual.club_nautico_api.Repository;

import org.atos.dual.club_nautico_api.Model.Barco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarcoRepository extends JpaRepository<Barco, Long> {

    List<Barco> findAllByPropietarioId(Long id);
    Barco findByNombre(String s);
}
