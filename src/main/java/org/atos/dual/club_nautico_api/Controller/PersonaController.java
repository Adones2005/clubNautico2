package org.atos.dual.club_nautico_api.Controller;

import org.atos.dual.club_nautico_api.DTO.PersonaDTO;
import org.atos.dual.club_nautico_api.Service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    @Autowired
    private PersonaService personaService;


    @GetMapping
    public ResponseEntity<?> getAllPersonas() {
        return personaService.getAllPersonas();
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getPersona(@PathVariable Long id) {
        return personaService.getPersona(id);
    }


    @PostMapping
    public ResponseEntity<?> savePersona(@RequestBody PersonaDTO personaDTO) {
        return personaService.savePersona(personaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePersona(@PathVariable Long id, @RequestBody PersonaDTO personaDTO) {
        return personaService.updatePersona(id, personaDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePersona(@PathVariable Long id) {
        return personaService.deletePersona(id);
    }
}
