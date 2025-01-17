package org.atos.dual.club_nautico_api.Controller;

import org.atos.dual.club_nautico_api.DTO.ViajeDTO;
import org.atos.dual.club_nautico_api.Service.ViajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/viajes")
public class ViajeController {
    @Autowired
    private ViajeService viajeService;

    @GetMapping
    public ResponseEntity<List<ViajeDTO>> getAllViajes() {
        return ResponseEntity.ok(viajeService.getAllViajes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViajeDTO> getViaje(@PathVariable Long id) {
        return viajeService.getViaje(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<ViajeDTO> createViaje(@RequestBody ViajeDTO viajeDTO) {
        ViajeDTO savedViaje = viajeService.saveViaje(viajeDTO);
        return new ResponseEntity<>(savedViaje, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ViajeDTO> updateViaje(@PathVariable Long id, @RequestBody ViajeDTO viajeDTO) {
        return viajeService.getViaje(id)
                .map(existingViaje -> {
                    viajeDTO.setId(existingViaje.getId()); // Asegura que se actualiza el mismo viaje
                    ViajeDTO updatedViaje = viajeService.saveViaje(viajeDTO);
                    return ResponseEntity.ok(updatedViaje);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteViaje(@PathVariable Long id) {
        if (viajeService.getViaje(id).isPresent()) {
            viajeService.deleteViaje(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
