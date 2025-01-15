package org.atos.dual.club_nautico_api.Service;


import org.atos.dual.club_nautico_api.Model.Barco;
import org.atos.dual.club_nautico_api.Repository.BarcoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BarcoService {
    @Autowired
    private BarcoRepository barcoRepository;

    public List<Barco> getAllBarcos() {
        return barcoRepository.findAll();
    }

    public Optional<Barco> getBarco(Long id) {
        return barcoRepository.findById(id);
    }

    public Barco saveBarco(Barco barco) {
        return barcoRepository.save(barco);
    }

    public void deleteBarco(Long id) {
        barcoRepository.deleteById(id);
    }
}
