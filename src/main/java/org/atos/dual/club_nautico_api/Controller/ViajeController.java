package org.atos.dual.club_nautico_api.Controller;

import org.atos.dual.club_nautico_api.DTO.ViajeDTO;
import org.atos.dual.club_nautico_api.Service.ViajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/viajes")
public class ViajeController {

    @Autowired
    private ViajeService viajeService;

    // Obtener todos los viajes
    @GetMapping
    public ResponseEntity<?> getAllViajes() {
        return viajeService.getAllViajes(); // Delegamos la respuesta completa al servicio
    }

    // Obtener un viaje por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getViaje(@PathVariable Long id) {
        return viajeService.getViaje(id); // El servicio ya gestiona los posibles casos
    }

    // Crear un nuevo viaje
    @PostMapping
    public ResponseEntity<?> createViaje(@RequestBody ViajeDTO viajeDTO) {
        return viajeService.saveViaje(viajeDTO); // Delegamos la creación al servicio
    }

    // Actualizar un viaje existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updateViaje(@PathVariable Long id, @RequestBody ViajeDTO viajeDTO) {
        viajeDTO.setId(id); // Aseguramos que el ID del recurso sea el correcto
        return viajeService.updateViaje(id, viajeDTO); // El servicio gestiona validaciones y errores
    }

    // Eliminar un viaje
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteViaje(@PathVariable Long id) {
        return viajeService.deleteViaje(id); // El servicio ya maneja la eliminación y los errores
    }
}
