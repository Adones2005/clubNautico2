package org.atos.dual.club_nautico_api.Mappers;

import org.atos.dual.club_nautico_api.DTO.ViajeDTO;
import org.atos.dual.club_nautico_api.Model.Barco;
import org.atos.dual.club_nautico_api.Model.Persona;
import org.atos.dual.club_nautico_api.Model.Viaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// Mapper para Viaje
@Component
public class ViajeMapper {

    @Autowired
    BarcoMapper barcoMapper;

    @Autowired
    PersonaMapper personaMapper;



    public ViajeDTO toDTO(Viaje viaje) {
        ViajeDTO dto = new ViajeDTO();
        dto.setId(viaje.getId());
        dto.setDescripcion(viaje.getDescripcion());
        dto.setFechaHora(viaje.getFechaHora());
        dto.setBarco(barcoMapper.toDTO(viaje.getBarco()));
        dto.setOrganizador(personaMapper.toDTO(viaje.getOrganizador()));
        return dto;
    }

    public Viaje toEntity(ViajeDTO dto) {
        Viaje viaje = new Viaje();
        viaje.setId(dto.getId());
        viaje.setDescripcion(dto.getDescripcion());
        viaje.setFechaHora(dto.getFechaHora());
        viaje.setBarco(barcoMapper.toEntity(dto.getBarco()));
        viaje.setOrganizador(personaMapper.toEntity((dto.getOrganizador())));
        return viaje;
    }
}
