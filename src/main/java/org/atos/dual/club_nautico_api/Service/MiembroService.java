package org.atos.dual.club_nautico_api.Service;

import org.atos.dual.club_nautico_api.DTO.MiembroDTO;
import org.atos.dual.club_nautico_api.Mappers.MiembroMapper;
import org.atos.dual.club_nautico_api.Model.Miembro;
import org.atos.dual.club_nautico_api.Model.Persona;
import org.atos.dual.club_nautico_api.Repository.MiembroRepository;
import org.atos.dual.club_nautico_api.Repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MiembroService {

    @Autowired
    private MiembroRepository miembroRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private MiembroMapper miembroMapper;

    public List<MiembroDTO> getAllMiembros() {
        return miembroRepository.findAll().stream()
                .map(miembroMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<MiembroDTO> getMiembro(Long id) {
        return miembroRepository.findById(id)
                .map(miembroMapper::toDTO);
    }

    public MiembroDTO saveMiembro(MiembroDTO miembroDTO) {
        Miembro miembro = miembroMapper.toEntity(miembroDTO);
        Miembro savedMiembro = miembroRepository.save(miembro);
        return miembroMapper.toDTO(savedMiembro);
    }

    public void deleteMiembro(Long id) {
        miembroRepository.deleteById(id);
    }
}

