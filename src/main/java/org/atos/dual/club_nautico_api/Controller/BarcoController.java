package org.atos.dual.club_nautico_api.Controller;

import org.atos.dual.club_nautico_api.DTO.BarcoDTO;
import org.atos.dual.club_nautico_api.Service.BarcoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/barcos")
public class BarcoController {
    @Autowired
    private BarcoService barcoService;

    @GetMapping
    public ResponseEntity<List<BarcoDTO>> getAllBarcos() {
        List<BarcoDTO> barcos = barcoService.getAllBarcos();
        return ResponseEntity.ok(barcos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BarcoDTO> getBarco(@PathVariable Long id) {
        return barcoService.getBarco(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<BarcoDTO> createBarco(@RequestBody BarcoDTO barcoDTO) {
        BarcoDTO createdBarco = barcoService.saveBarco(barcoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBarco);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BarcoDTO> updateBarco(@PathVariable Long id, @RequestBody BarcoDTO barcoDTO) {
        return barcoService.getBarco(id)
                .map(existingBarco -> {
                    barcoDTO.setId(id); // Asegurar que se use el ID del recurso
                    BarcoDTO updatedBarco = barcoService.saveBarco(barcoDTO);
                    return ResponseEntity.ok(updatedBarco);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBarco(@PathVariable Long id) {
        if (barcoService.getBarco(id).isPresent()) {
            barcoService.deleteBarco(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
