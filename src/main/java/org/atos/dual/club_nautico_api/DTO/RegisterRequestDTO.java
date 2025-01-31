package org.atos.dual.club_nautico_api.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    @NotBlank
    private String nombre;

    @NotBlank
    private String apellidos;

    private String telefono;
    private String direccion;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private boolean esPatron;


}