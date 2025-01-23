package org.atos.dual.club_nautico_api.Service;

import org.atos.dual.club_nautico_api.DTO.MiembroDTO;
import org.atos.dual.club_nautico_api.Mappers.MiembroMapper;
import org.atos.dual.club_nautico_api.Model.Miembro;
import org.atos.dual.club_nautico_api.Model.Persona;
import org.atos.dual.club_nautico_api.Repository.MiembroRepository;
import org.atos.dual.club_nautico_api.Repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<?> getAllMiembros() {
        try {
            List<MiembroDTO> miembros = miembroRepository.findAll().stream()
                    .map(miembroMapper::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(miembros);
        } catch (Exception e) {
            System.err.println("Error al obtener la lista de miembros: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener la lista de miembros.");
        }
    }

    public ResponseEntity<?> getMiembro(Long id) {
        try {
            Optional<Miembro> miembroOptional = miembroRepository.findById(id);
            if (miembroOptional.isEmpty()) {
                String errorMessage = "Error: El miembro con ID " + id + " no existe.";
                System.out.println(errorMessage);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
            MiembroDTO miembroDTO = miembroMapper.toDTO(miembroOptional.get());
            return ResponseEntity.ok(miembroDTO);
        } catch (Exception e) {
            System.err.println("Error al obtener el miembro: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado al obtener el miembro.");
        }
    }

    public ResponseEntity<?> saveMiembro(MiembroDTO miembroDTO) {
        try {
            // Verifica si la persona ya está asociada a un miembro
            if (miembroRepository.existsByPersonaId(miembroDTO.getPersona().getId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error: La persona con ID " + miembroDTO.getPersona().getId() + " ya es miembro.");
            }

            Persona persona = personaRepository.findById(miembroDTO.getPersona().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Error: La persona con ID " + miembroDTO.getPersona().getId() + " no existe."));

            Miembro miembro = miembroMapper.toEntity(miembroDTO);
            miembro.setPersona(persona);
            Miembro savedMiembro = miembroRepository.save(miembro);

            return ResponseEntity.status(HttpStatus.CREATED).body(miembroMapper.toDTO(savedMiembro));
        } catch (RuntimeException e) {
            System.err.println("Error al crear el miembro: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al crear el miembro: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado al crear el miembro.");
        }
    }

    public ResponseEntity<?> updateMiembro(Long id, MiembroDTO miembroDTO) {
        try {
            Miembro miembro = miembroRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Error: El miembro con ID " + id + " no existe."));

            // Verifica si la persona ya está asociada a otro miembro
            if (miembroRepository.existsByPersonaId(miembroDTO.getPersona().getId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error: La persona con ID " + miembroDTO.getPersona().getId() + " ya es miembro.");
            }

            Persona persona = personaRepository.findById(miembroDTO.getPersona().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Error: La persona con ID " + miembroDTO.getPersona().getId() + " no existe."));

            miembro.setPersona(persona); // Actualiza las propiedades
            Miembro updatedMiembro = miembroRepository.save(miembro);

            return ResponseEntity.ok(miembroMapper.toDTO(updatedMiembro));
        } catch (RuntimeException e) {
            System.err.println("Error al actualizar el miembro: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al actualizar el miembro: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado al actualizar el miembro.");
        }
    }


    public ResponseEntity<?> deleteMiembro(Long id) {
        try {
            if (!miembroRepository.existsById(id)) {
                String errorMessage = "Error: El miembro con ID " + id + " no existe.";
                System.out.println(errorMessage);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
            miembroRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            System.err.println("Error inesperado al eliminar el miembro: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado al eliminar el miembro.");
        }
    }
}
