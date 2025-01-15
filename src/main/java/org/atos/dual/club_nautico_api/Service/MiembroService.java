package org.atos.dual.club_nautico_api.Service;


import org.atos.dual.club_nautico_api.Model.Miembro;
import org.atos.dual.club_nautico_api.Repository.MiembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MiembroService {
    @Autowired
    private MiembroRepository miembroRepository;

    public List<Miembro> getAllMiembros() {
        return miembroRepository.findAll();
    }

    public Optional<Miembro> getMiembro(Long id) {
        return miembroRepository.findById(id);
    }

    public Miembro saveMiembro(Miembro miembro) {
        return miembroRepository.save(miembro);
    }

    public void deleteMiembro(Long id) {
        miembroRepository.deleteById(id);
    }
}