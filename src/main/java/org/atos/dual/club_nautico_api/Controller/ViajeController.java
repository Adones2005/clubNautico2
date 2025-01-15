package org.atos.dual.club_nautico_api.Controller;

import org.atos.dual.club_nautico_api.DTO.ViajeDTO;
import org.atos.dual.club_nautico_api.Model.Viaje;
import org.atos.dual.club_nautico_api.Service.ViajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/viajes")
class ViajeController {
    @Autowired
    private ViajeService viajeService;

    @GetMapping
    public ResponseEntity<List<Viaje>> getAllViajes() {
        return ResponseEntity.ok(viajeService.getAllViajes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Viaje> getViaje(@PathVariable Long id) {
        return viajeService.getViaje(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Viaje> createViaje(@RequestBody ViajeDTO viajeDTO) {
        Viaje viaje = new Viaje();
        viaje.setDescripcion(viajeDTO.getDescripcion());
        return new ResponseEntity<>(viajeService.saveViaje(viaje), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Viaje> updateViaje(@PathVariable Long id, @RequestBody ViajeDTO viajeDTO) {
        return viajeService.getViaje(id)
                .map(existingViaje -> {
                    existingViaje.setDescripcion(viajeDTO.getDescripcion());
                    return ResponseEntity.ok(viajeService.saveViaje(existingViaje));
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
