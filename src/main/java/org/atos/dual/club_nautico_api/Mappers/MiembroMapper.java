package org.atos.dual.club_nautico_api.Mappers;

import org.atos.dual.club_nautico_api.DTO.MiembroDTO;
import org.atos.dual.club_nautico_api.Model.Miembro;
import org.springframework.stereotype.Component;

@Component
public class MiembroMapper {

    public MiembroDTO toDTO(Miembro miembro) {
        MiembroDTO dto = new MiembroDTO();
        dto.setId(miembro.getId());
        dto.setNombre(miembro.getNombre());
        dto.setApellidos(miembro.getApellidos());
        dto.setDNI(miembro.getDNI());
        dto.setTelefono(miembro.getTelefono());
        dto.setDireccion(miembro.getDireccion());
        dto.setEsPatron(miembro.getEsPatron());
        return dto;
    }

    public Miembro toEntity(MiembroDTO dto) {
        Miembro miembro = new Miembro();
        miembro.setId(dto.getId());
        miembro.setNombre(dto.getNombre());
        miembro.setApellidos(dto.getApellidos());
        miembro.setDNI(dto.getDNI());
        miembro.setTelefono(dto.getTelefono());
        miembro.setDireccion(dto.getDireccion());
        miembro.setEsPatron(dto.getEsPatron());
        return miembro;
    }
}

