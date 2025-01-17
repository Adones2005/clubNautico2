package org.atos.dual.club_nautico_api.Controller;

import org.atos.dual.club_nautico_api.DTO.MiembroDTO;
import org.atos.dual.club_nautico_api.Service.MiembroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/miembros")
public class MiembroController {
    @Autowired
    private MiembroService miembroService;

    @GetMapping
    public ResponseEntity<List<MiembroDTO>> getAllMiembros() {
        List<MiembroDTO> miembros = miembroService.getAllMiembros();
        return ResponseEntity.ok(miembros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MiembroDTO> getMiembro(@PathVariable Long id) {
        return miembroService.getMiembro(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping()
    public ResponseEntity<MiembroDTO> createMiembro(@RequestBody MiembroDTO miembroDTO) {
        MiembroDTO createdMiembro = miembroService.saveMiembro(miembroDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMiembro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MiembroDTO> updateMiembro(@PathVariable Long id, @RequestBody MiembroDTO miembroDTO) {
        return miembroService.getMiembro(id)
                .map(existingMiembro -> {
                    MiembroDTO updatedMiembro = miembroService.saveMiembro(miembroDTO);
                    return ResponseEntity.ok(updatedMiembro);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMiembro(@PathVariable Long id) {
        if (miembroService.getMiembro(id).isPresent()) {
            miembroService.deleteMiembro(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
