package org.atos.dual.club_nautico_api.Controller;


import org.atos.dual.club_nautico_api.DTO.PersonaDTO;
import org.atos.dual.club_nautico_api.Model.Persona;
import org.atos.dual.club_nautico_api.Service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personas")
class PersonaController {
    @Autowired
    private PersonaService personaService;

    @GetMapping
    public ResponseEntity<List<Persona>> getAllPersonas() {
        return ResponseEntity.ok(personaService.getAllPersonas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> getPersona(@PathVariable Long id) {
        return personaService.getPersona(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Persona> createPersona(@RequestBody PersonaDTO personaDTO) {
        Persona persona = new Persona();
        persona.setNombre(personaDTO.getNombre());
        persona.setApellidos(personaDTO.getApellidos());
        persona.setTelefono(personaDTO.getTelefono());
        persona.setDireccion(personaDTO.getDireccion());
        persona.setEsPatron(personaDTO.getEsPatron());
        return new ResponseEntity<>(personaService.savePersona(persona), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> updatePersona(@PathVariable Long id, @RequestBody PersonaDTO personaDTO) {
        return personaService.getPersona(id)
                .map(existingPersona -> {
                    existingPersona.setNombre(personaDTO.getNombre());
                    existingPersona.setApellidos(personaDTO.getApellidos());
                    existingPersona.setTelefono(personaDTO.getTelefono());
                    existingPersona.setDireccion(personaDTO.getDireccion());
                    existingPersona.setEsPatron(personaDTO.getEsPatron());
                    return ResponseEntity.ok(personaService.savePersona(existingPersona));
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersona(@PathVariable Long id) {
        if (personaService.getPersona(id).isPresent()) {
            personaService.deletePersona(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
