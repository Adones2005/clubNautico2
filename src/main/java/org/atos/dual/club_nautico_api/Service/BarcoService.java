package org.atos.dual.club_nautico_api.Service;

import org.atos.dual.club_nautico_api.DTO.BarcoDTO;
import org.atos.dual.club_nautico_api.Mappers.BarcoMapper;
import org.atos.dual.club_nautico_api.Model.Barco;
import org.atos.dual.club_nautico_api.Model.Miembro;
import org.atos.dual.club_nautico_api.Repository.BarcoRepository;
import org.atos.dual.club_nautico_api.Repository.MiembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BarcoService {

    @Autowired
    private BarcoRepository barcoRepository;

    @Autowired
    private MiembroRepository miembroRepository;

    @Autowired
    private BarcoMapper barcoMapper;

    // Obtener todos los barcos
    public ResponseEntity<?> getAllBarcos() {
        try {
            List<BarcoDTO> barcos = barcoRepository.findAll().stream()
                    .map(barcoMapper::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(barcos);
        } catch (Exception e) {
            System.err.println("Error al obtener la lista de barcos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener la lista de barcos.");
        }
    }

    // Obtener un barco por ID
    public ResponseEntity<?> getBarco(Long id) {
        try {
            Optional<Barco> barcoOptional = barcoRepository.findById(id);
            if (barcoOptional.isEmpty()) {
                String errorMessage = "Error: El barco con ID " + id + " no existe.";
                System.out.println(errorMessage);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
            BarcoDTO barcoDTO = barcoMapper.toDTO(barcoOptional.get());
            return ResponseEntity.ok(barcoDTO);
        } catch (Exception e) {
            System.err.println("Error al obtener el barco: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado al obtener el barco.");
        }
    }

    public ResponseEntity<?> getAllBarcosByMiembro(Long id) {
        try {
            // Verifica si el miembro con el ID dado existe
            if (!miembroRepository.existsById(id)) {
                String errorMessage = "Error: El miembro con ID " + id + " no existe.";
                System.out.println(errorMessage);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }

            // Si el miembro existe, obtiene los barcos asociados
            List<BarcoDTO> barcos = barcoRepository.findAllByPropietarioId(id).stream()
                    .map(barcoMapper::toDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(barcos);
        } catch (Exception e) {
            System.err.println("Error al obtener los barcos del miembro: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener los barcos del miembro.");
        }
    }
    public ResponseEntity<?> saveBarco(BarcoDTO barcoDTO) {
        try {
            // Verifica si el propietario (miembro) existe, pero solo si el id no es nulo
            if (barcoDTO.getPropietario() != null && !miembroRepository.existsById(barcoDTO.getPropietario().getId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error: El miembro con ID " + barcoDTO.getPropietario().getId() + " no existe.");
            }

            // Convierte el DTO a entidad
            Barco barco = barcoMapper.toEntity(barcoDTO);

            // Si el propietario no es nulo, busca el miembro por id y asigna al barco
            if (barcoDTO.getPropietario() != null) {
                Miembro propietario = miembroRepository.findById(barcoDTO.getPropietario().getId())
                        .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));
                barco.setPropietario(propietario); // Asigna el miembro como propietario del barco
            }

            // Guarda el barco en la base de datos
            Barco savedBarco = barcoRepository.save(barco);

            // Devuelve el barco guardado como DTO
            return ResponseEntity.status(HttpStatus.CREATED).body(barcoMapper.toDTO(savedBarco));
        } catch (Exception e) {
            System.err.println("Error al crear el barco: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado al crear el barco.");
        }
    }

    // Actualizar un barco
    public ResponseEntity<?> updateBarco(Long id, BarcoDTO barcoDTO) {
        try {
            // Buscar el barco en la base de datos
            Optional<Barco> barcoOptional = barcoRepository.findById(id);

            if (barcoOptional.isEmpty()) {
                String errorMessage = "Error: El barco con ID " + id + " no existe.";
                System.out.println(errorMessage);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }

            // Obtener el barco actual
            Barco barco = barcoOptional.get();

            // Verificar que el propietario (Miembro) existe en la base de datos
            Optional<Miembro> miembroOptional = miembroRepository.findById(barcoDTO.getPropietario().getId());
            if (miembroOptional.isEmpty()) {
                String errorMessage = "Error: El miembro con ID " + barcoDTO.getPropietario().getId() + " no existe.";
                System.out.println(errorMessage);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }

            // Asignar el propietario al barco (el propietario es un Miembro)
            Miembro propietario = miembroOptional.get();
            barco.setPropietario(propietario);

            // Actualizar el resto de los detalles del barco
            barco.setNombre(barcoDTO.getNombre());
            barco.setMatricula(barcoDTO.getMatricula());
            barco.setAmarre(barcoDTO.getAmarre());
            barco.setTarifa(barcoDTO.getTarifa());

            // Guardar el barco actualizado
            Barco updatedBarco = barcoRepository.save(barco);

            // Convertir el barco actualizado a DTO y devolver la respuesta
            return ResponseEntity.ok(barcoMapper.toDTO(updatedBarco));
        } catch (Exception e) {
            System.err.println("Error al actualizar el barco: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado al actualizar el barco.");
        }
    }


    // Eliminar un barco
    public ResponseEntity<?> deleteBarco(Long id) {
        try {
            if (!barcoRepository.existsById(id)) {
                String errorMessage = "Error: El barco con ID " + id + " no existe.";
                System.out.println(errorMessage);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
            barcoRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            System.err.println("Error inesperado al eliminar el barco: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado al eliminar el barco.");
        }
    }
}
