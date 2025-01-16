package org.atos.dual.club_nautico_api.Service;


import org.atos.dual.club_nautico_api.DTO.BarcoDTO;
import org.atos.dual.club_nautico_api.Mappers.BarcoMapper;
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

    @Autowired
    private BarcoMapper barcoMapper;

    public List<BarcoDTO> getAllBarcos() {
        List<Barco> barcos = barcoRepository.findAll();
        return barcos.stream()
                .map(barcoMapper::toDTO)
                .toList(); // Convierte las entidades a DTOs
    }

    public Optional<BarcoDTO> getBarco(Long id) {
        return barcoRepository.findById(id)
                .map(barcoMapper::toDTO); // Convierte la entidad encontrada a DTO
    }

    public BarcoDTO saveBarco(BarcoDTO barcoDTO) {
        Barco barco = barcoMapper.toEntity(barcoDTO); // Convierte el DTO a entidad
        Barco savedBarco = barcoRepository.save(barco);
        return barcoMapper.toDTO(savedBarco); // Convierte la entidad guardada a DTO
    }

    public void deleteBarco(Long id) {
        barcoRepository.deleteById(id);
    }
}

