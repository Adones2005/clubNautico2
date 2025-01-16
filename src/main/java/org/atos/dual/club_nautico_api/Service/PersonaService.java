package org.atos.dual.club_nautico_api.Service;

import org.atos.dual.club_nautico_api.DTO.PersonaDTO;
import org.atos.dual.club_nautico_api.Mappers.PersonaMapper;
import org.atos.dual.club_nautico_api.Model.Persona;
import org.atos.dual.club_nautico_api.Repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonaService {
    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private PersonaMapper personaMapper;

    public List<PersonaDTO> getAllPersonas() {
        return personaRepository.findAll().stream()
                .map(personaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<PersonaDTO> getPersona(Long id) {
        return personaRepository.findById(id)
                .map(personaMapper::toDTO);
    }

    public PersonaDTO savePersona(PersonaDTO personaDTO) {
        Persona persona = personaMapper.toEntity(personaDTO);
        Persona savedPersona = personaRepository.save(persona);
        return personaMapper.toDTO(savedPersona);
    }

    public void deletePersona(Long id) {
        personaRepository.deleteById(id);
    }
}
