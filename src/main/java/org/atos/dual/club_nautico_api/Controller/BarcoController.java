package org.atos.dual.club_nautico_api.Controller;


import org.atos.dual.club_nautico_api.DTO.BarcoDTO;
import org.atos.dual.club_nautico_api.Model.Barco;
import org.atos.dual.club_nautico_api.Service.BarcoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controllers
@RestController
@RequestMapping("/api/barcos")
class BarcoController {
    @Autowired
    private BarcoService barcoService;

    @GetMapping
    public ResponseEntity<List<Barco>> getAllBarcos() {
        return ResponseEntity.ok(barcoService.getAllBarcos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Barco> getBarco(@PathVariable Long id) {
        return barcoService.getBarco(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Barco> createBarco(@RequestBody BarcoDTO barcoDTO) {
        Barco barco = new Barco();
        barco.setMatricula(barcoDTO.getMatricula());
        barco.setNombre(barcoDTO.getNombre());
        barco.setAmarre(barcoDTO.getAmarre());
        barco.setTarifa(barcoDTO.getTarifa());
        return new ResponseEntity<>(barcoService.saveBarco(barco), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Barco> updateBarco(@PathVariable Long id, @RequestBody BarcoDTO barcoDTO) {
        return barcoService.getBarco(id)
                .map(existingBarco -> {
                    existingBarco.setNombre(barcoDTO.getNombre());
                    existingBarco.setAmarre(barcoDTO.getAmarre());
                    existingBarco.setTarifa(barcoDTO.getTarifa());
                    return ResponseEntity.ok(barcoService.saveBarco(existingBarco));
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
