package org.atos.dual.club_nautico_api.Mappers;

import org.atos.dual.club_nautico_api.DTO.PersonaDTO;
import org.atos.dual.club_nautico_api.Model.Persona;
import org.springframework.stereotype.Component;

@Component
public class PersonaMapper {

    public PersonaDTO toDTO(Persona persona) {
        PersonaDTO dto = new PersonaDTO();
        dto.setId(persona.getId());
        dto.setNombre(persona.getNombre());
        dto.setApellidos(persona.getApellidos());
        dto.setTelefono(persona.getTelefono());
        dto.setDireccion(persona.getDireccion());
        dto.setEsPatron(persona.getEsPatron());
        return dto;
    }

    public Persona toEntity(PersonaDTO dto) {
        Persona persona = new Persona();
        persona.setId(dto.getId());
        persona.setNombre(dto.getNombre());
        persona.setApellidos(dto.getApellidos());
        persona.setTelefono(dto.getTelefono());
        persona.setDireccion(dto.getDireccion());
        persona.setEsPatron(dto.getEsPatron());
        return persona;
    }
}
