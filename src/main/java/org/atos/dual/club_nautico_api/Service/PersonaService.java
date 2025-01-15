package org.atos.dual.club_nautico_api.Service;

import org.atos.dual.club_nautico_api.Model.Persona;
import org.atos.dual.club_nautico_api.Repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {
    @Autowired
    private PersonaRepository personaRepository;

    public List<Persona> getAllPersonas() {
        return personaRepository.findAll();
    }

    public Optional<Persona> getPersona(Long id) {
        return personaRepository.findById(id);
    }

    public Persona savePersona(Persona persona) {
        return personaRepository.save(persona);
    }

    public void deletePersona(Long id) {
        personaRepository.deleteById(id);
    }
}