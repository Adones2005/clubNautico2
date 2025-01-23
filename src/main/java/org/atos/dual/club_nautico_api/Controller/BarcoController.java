package org.atos.dual.club_nautico_api.Controller;

import org.atos.dual.club_nautico_api.DTO.BarcoDTO;
import org.atos.dual.club_nautico_api.Service.BarcoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/barcos")
public class BarcoController {

    @Autowired
    private BarcoService barcoService;

    @GetMapping
    public ResponseEntity<?> getAllBarcos() {
        return barcoService.getAllBarcos(); // Delegamos la respuesta completa al servicio
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBarco(@PathVariable Long id) {
        return barcoService.getBarco(id); // El servicio ya gestiona los posibles casos
    }

    @GetMapping("/miembro/{id}")
    public ResponseEntity<?> getAllBarcosByMiembro(@PathVariable Long id) {
        return barcoService.getAllBarcosByMiembro(id); // El servicio gestiona la obtención de barcos por miembro
    }

    @PostMapping
    public ResponseEntity<?> createBarco(@RequestBody BarcoDTO barcoDTO) {
        return barcoService.saveBarco(barcoDTO); // Delegamos la creación al servicio
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBarco(@PathVariable Long id, @RequestBody BarcoDTO barcoDTO) {
        barcoDTO.setId(id); // Aseguramos que el ID del recurso sea el correcto
        return barcoService.updateBarco(id, barcoDTO); // El servicio gestiona validaciones y errores
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBarco(@PathVariable Long id) {
        return barcoService.deleteBarco(id); // El servicio ya maneja la eliminación y los errores
    }
}
