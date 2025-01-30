package org.atos.dual.club_nautico_api.Controller;

import org.atos.dual.club_nautico_api.DTO.MiembroDTO;
import org.atos.dual.club_nautico_api.Service.MiembroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/miembros")
public class MiembroController {

    @Autowired
    private MiembroService miembroService;

    @GetMapping
    public ResponseEntity<?> getAllMiembros() {
        return miembroService.getAllMiembros(); // Delegamos la respuesta completa al servicio
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMiembro(@PathVariable Long id) {
        return miembroService.getMiembro(id); // El servicio ya gestiona los posibles casos
    }

    @PostMapping
    public ResponseEntity<?> createMiembro(@RequestBody MiembroDTO miembroDTO) {
        return miembroService.saveMiembro(miembroDTO); // Delegamos la creación al servicio
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMiembro(@PathVariable Long id, @RequestBody MiembroDTO miembroDTO) {
        miembroDTO.setId(id); // Aseguramos que el ID del recurso sea el correcto
        return miembroService.updateMiembro(id, miembroDTO); // El servicio gestiona validaciones y errores
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMiembro(@PathVariable Long id) {
        return miembroService.deleteMiembro(id); // El servicio ya maneja la eliminación y los errores
    }
}
