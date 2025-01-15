package org.atos.dual.club_nautico_api.Controller;

import org.atos.dual.club_nautico_api.DTO.MiembroDTO;
import org.atos.dual.club_nautico_api.Model.Miembro;
import org.atos.dual.club_nautico_api.Service.MiembroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/miembros")
class MiembroController {
    @Autowired
    private MiembroService miembroService;

    @GetMapping
    public ResponseEntity<List<Miembro>> getAllMiembros() {
        return ResponseEntity.ok(miembroService.getAllMiembros());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Miembro> getMiembro(@PathVariable Long id) {
        return miembroService.getMiembro(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Miembro> createMiembro(@RequestBody MiembroDTO miembroDTO) {
        Miembro miembro = new Miembro();
        miembro.setNombre(miembroDTO.getNombre());
        miembro.setApellidos(miembroDTO.getApellidos());
        return new ResponseEntity<>(miembroService.saveMiembro(miembro), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Miembro> updateMiembro(@PathVariable Long id, @RequestBody MiembroDTO miembroDTO) {
        return miembroService.getMiembro(id)
                .map(existingMiembro -> {
                    existingMiembro.setNombre(miembroDTO.getNombre());
                    existingMiembro.setApellidos(miembroDTO.getApellidos());
                    return ResponseEntity.ok(miembroService.saveMiembro(existingMiembro));
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
