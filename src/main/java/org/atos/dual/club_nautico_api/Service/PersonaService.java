package org.atos.dual.club_nautico_api.Service;

import org.atos.dual.club_nautico_api.DTO.PersonaDTO;
import org.atos.dual.club_nautico_api.Mappers.PersonaMapper;
import org.atos.dual.club_nautico_api.Model.Persona;
import org.atos.dual.club_nautico_api.Repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<?> getAllPersonas() {
        try {
            List<PersonaDTO> personas = personaRepository.findAll().stream()
                    .map(personaMapper::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(personas);
        } catch (Exception e) {
            System.err.println("Error al obtener la lista de personas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener la lista de personas.");
        }
    }

    public ResponseEntity<?> getPersona(Long id) {
        try {
            Optional<Persona> personaOptional = personaRepository.findById(id);
            if (personaOptional.isEmpty()) {
                String errorMessage = "Error: La persona con ID " + id + " no existe.";
                System.out.println(errorMessage);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
            PersonaDTO personaDTO = personaMapper.toDTO(personaOptional.get());
            return ResponseEntity.ok(personaDTO);
        } catch (Exception e) {
            System.err.println("Error al obtener la persona: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado al obtener la persona.");
        }
    }

    public ResponseEntity<?> savePersona(PersonaDTO personaDTO) {
        try {
            Persona persona = personaMapper.toEntity(personaDTO);
            Persona savedPersona = personaRepository.save(persona);
            return ResponseEntity.status(HttpStatus.CREATED).body(personaMapper.toDTO(savedPersona));
        } catch (Exception e) {
            System.err.println("Error inesperado al guardar la persona: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado al guardar la persona.");
        }
    }

    public ResponseEntity<?> updatePersona(Long id, PersonaDTO personaDTO) {
        try {
            // Verifica si la persona existe
            Persona persona = personaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Error: La persona con ID " + id + " no existe."));

            // Actualiza los campos de la persona
            persona.setNombre(personaDTO.getNombre());
            persona.setApellidos(personaDTO.getApellidos());
            persona.setDireccion(personaDTO.getDireccion());
            persona.setTelefono(personaDTO.getTelefono());
            persona.setEsPatron(personaDTO.isEsPatron());

            // Guarda la persona actualizada
            Persona updatedPersona = personaRepository.save(persona);
            return ResponseEntity.ok(personaMapper.toDTO(updatedPersona));
        } catch (RuntimeException e) {
            System.err.println("Error al actualizar la persona: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al actualizar la persona: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado al actualizar la persona.");
        }
    }


    public ResponseEntity<?> deletePersona(Long id) {
        try {
            if (!personaRepository.existsById(id)) {
                String errorMessage = "Error: La persona con ID " + id + " no existe.";
                System.out.println(errorMessage);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
            personaRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            System.err.println("Error inesperado al eliminar la persona: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado al eliminar la persona.");
        }
    }
}
