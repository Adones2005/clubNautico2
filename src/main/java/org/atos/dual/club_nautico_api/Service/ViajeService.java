package org.atos.dual.club_nautico_api.Service;

import org.atos.dual.club_nautico_api.DTO.ViajeDTO;
import org.atos.dual.club_nautico_api.Mappers.ViajeMapper;
import org.atos.dual.club_nautico_api.Model.Barco;
import org.atos.dual.club_nautico_api.Model.Persona;
import org.atos.dual.club_nautico_api.Model.Viaje;
import org.atos.dual.club_nautico_api.Repository.BarcoRepository;
import org.atos.dual.club_nautico_api.Repository.PersonaRepository;
import org.atos.dual.club_nautico_api.Repository.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ViajeService {

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private BarcoRepository barcoRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private ViajeMapper viajeMapper;

    // Obtener todos los viajes
    public ResponseEntity<?> getAllViajes() {
        try {
            List<ViajeDTO> viajes = viajeRepository.findAll().stream()
                    .map(viajeMapper::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(viajes);
        } catch (Exception e) {
            System.err.println("Error al obtener la lista de viajes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener la lista de viajes.");
        }
    }

    // Obtener un viaje por ID
    public ResponseEntity<?> getViaje(Long id) {
        try {
            Optional<Viaje> viajeOptional = viajeRepository.findById(id);
            if (viajeOptional.isEmpty()) {
                String errorMessage = "Error: El viaje con ID " + id + " no existe.";
                System.out.println(errorMessage);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
            ViajeDTO viajeDTO = viajeMapper.toDTO(viajeOptional.get());
            return ResponseEntity.ok(viajeDTO);
        } catch (Exception e) {
            System.err.println("Error al obtener el viaje: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado al obtener el viaje.");
        }
    }

    public ResponseEntity<?> saveViaje(ViajeDTO viajeDTO) {
        try {
            // Verificar si el barco existe
            Optional<Barco> barcoOptional = barcoRepository.findById(viajeDTO.getBarco().getId());
            if (barcoOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Error: El barco con ID " + viajeDTO.getBarco().getId() + " no existe.");
            }

            // Verificar si la persona existe
            Optional<Persona> personaOptional = personaRepository.findById(viajeDTO.getOrganizador().getId());
            if (personaOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Error: La persona con ID " + viajeDTO.getOrganizador().getId() + " no existe.");
            }

            // Usar las entidades gestionadas
            Barco barco = barcoOptional.get();
            Persona organizador = personaOptional.get();

            // Mapear y asignar las entidades relacionadas
            Viaje viaje = viajeMapper.toEntity(viajeDTO);
            viaje.setBarco(barco);
            viaje.setOrganizador(organizador);

            // Guardar el viaje
            Viaje savedViaje = viajeRepository.save(viaje);
            return ResponseEntity.status(HttpStatus.CREATED).body(viajeMapper.toDTO(savedViaje));
        } catch (Exception e) {
            System.err.println("Error al crear el viaje: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado al crear el viaje.");
        }
    }


    // Actualizar un viaje existente
    public ResponseEntity<?> updateViaje(Long id, ViajeDTO viajeDTO) {
        try {
            // Verificar si el viaje existe
            Optional<Viaje> viajeOptional = viajeRepository.findById(id);
            if (viajeOptional.isEmpty()) {
                String errorMessage = "Error: El viaje con ID " + id + " no existe.";
                System.out.println(errorMessage);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }

            // Verificar si el barco existe
            Optional<Barco> barcoOptional = barcoRepository.findById(viajeDTO.getBarco().getId());
            if (barcoOptional.isEmpty()) {
                String errorMessage = "Error: El barco con ID " + viajeDTO.getBarco().getId() + " no existe.";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }

            // Verificar si la persona existe
            Optional<Persona> personaOptional = personaRepository.findById(viajeDTO.getOrganizador().getId());
            if (personaOptional.isEmpty()) {
                String errorMessage = "Error: La persona con ID " + viajeDTO.getOrganizador().getId() + " no existe.";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }

            // Actualizar los detalles del viaje
            Viaje viaje = viajeOptional.get();
            viaje.setDescripcion(viajeDTO.getDescripcion());
            viaje.setFechaHora(viajeDTO.getFechaHora());
            viaje.setBarco(barcoOptional.get());
            viaje.setOrganizador(personaOptional.get());

            Viaje updatedViaje = viajeRepository.save(viaje);
            return ResponseEntity.ok(viajeMapper.toDTO(updatedViaje));
        } catch (Exception e) {
            System.err.println("Error al actualizar el viaje: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado al actualizar el viaje.");
        }
    }

    // Eliminar un viaje
    public ResponseEntity<?> deleteViaje(Long id) {
        try {
            if (!viajeRepository.existsById(id)) {
                String errorMessage = "Error: El viaje con ID " + id + " no existe.";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
            viajeRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            System.err.println("Error al eliminar el viaje: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado al eliminar el viaje.");
        }
    }
}
