package org.atos.dual.club_nautico_api.Mappers;

import org.atos.dual.club_nautico_api.Model.Persona;
import org.atos.dual.club_nautico_api.DTO.PersonaDTO;
import org.springframework.stereotype.Component;

@Component
public class PersonaMapper {

    // Convierte de Persona a PersonaDTO
    public PersonaDTO toDTO(Persona persona) {
        if (persona == null) {
            return null;
        }
        PersonaDTO dto = new PersonaDTO();
        dto.setNombre(persona.getNombre());
        dto.setApellidos(persona.getApellidos());
        dto.setTelefono(persona.getTelefono());
        dto.setDireccion(persona.getDireccion());
        dto.setUsername(persona.getUsername());
        dto.setPassword(persona.getPassword());  // Si es necesario incluir el password
        dto.setEsPatron(persona.getEsPatron() != null ? persona.getEsPatron() : false); // Si es nulo, poner false
        return dto;
    }

    // Convierte de PersonaDTO a Persona
    public Persona toEntity(PersonaDTO dto) {
        if (dto == null) {
            return null;
        }
        // Usar el constructor para crear la entidad Persona
        Persona persona = new Persona(dto);
        return persona;
    }
}
