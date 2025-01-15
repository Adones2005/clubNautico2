package org.atos.dual.club_nautico_api.Service;

import org.atos.dual.club_nautico_api.Model.Viaje;
import org.atos.dual.club_nautico_api.Repository.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ViajeService {
    @Autowired
    private ViajeRepository viajeRepository;

    public List<Viaje> getAllViajes() {
        return viajeRepository.findAll();
    }

    public Optional<Viaje> getViaje(Long id) {
        return viajeRepository.findById(id);
    }

    public Viaje saveViaje(Viaje viaje) {
        return viajeRepository.save(viaje);
    }

    public void deleteViaje(Long id) {
        viajeRepository.deleteById(id);
    }
}
