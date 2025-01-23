package org.atos.dual.club_nautico_api.DTO;

import lombok.Data;

@Data
public class PersonaDTO {
    private Long id;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String direccion;
    private Boolean esPatron;
}