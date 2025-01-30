package org.atos.dual.club_nautico_api.Mappers;

import org.atos.dual.club_nautico_api.DTO.MiembroDTO;
import org.atos.dual.club_nautico_api.Model.Miembro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MiembroMapper {

    @Autowired
    private PersonaMapper personaMapper;

    public MiembroDTO toDTO(Miembro miembro) {
        if (miembro == null) {
            return null;
        }
        MiembroDTO dto = new MiembroDTO();
        dto.setId(miembro.getId());
        dto.setPersona(personaMapper.toDTO(miembro.getPersona())); // Mapeo de Persona
        return dto;
    }

    // Convierte de MiembroDTO a Miembro
    public Miembro toEntity(MiembroDTO dto) {
        if (dto == null) {
            return null;
        }
        Miembro miembro = new Miembro();
        miembro.setId(dto.getId());
        miembro.setPersona(personaMapper.toEntity(dto.getPersona())); // Mapeo de PersonaDTO
        return miembro;
    }
}

