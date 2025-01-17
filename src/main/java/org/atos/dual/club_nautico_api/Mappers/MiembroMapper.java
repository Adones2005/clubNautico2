package org.atos.dual.club_nautico_api.Mappers;

import org.atos.dual.club_nautico_api.DTO.BarcoDTO;
import org.atos.dual.club_nautico_api.DTO.MiembroDTO;
import org.atos.dual.club_nautico_api.Model.Barco;
import org.atos.dual.club_nautico_api.Model.Miembro;
import org.atos.dual.club_nautico_api.Model.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MiembroMapper {

    @Autowired
    private PersonaMapper personaMapper;

    @Autowired
    @Lazy
    private BarcoMapper barcoMapper;

    public MiembroDTO toDTO(Miembro miembro) {
        MiembroDTO dto = new MiembroDTO();
        dto.setPersonaDTO(personaMapper.toDTO(miembro.getPersona()));
        List<BarcoDTO> barcosDTO = miembro.getBarcos().stream()
                .map(barcoMapper::toDTO)
                .toList();
        dto.setBarcosDTO(barcosDTO);
        return dto;
    }

    public Miembro toEntity(MiembroDTO dto) {
        Miembro miembro = new Miembro();
        miembro.setPersona(personaMapper.toEntity(dto.getPersonaDTO()));
        List<Barco> barcos = dto.getBarcosDTO().stream()
                .map(barcoMapper::toEntity)
                .toList();
        miembro.setBarcos(barcos);
        return miembro;
    }
}

