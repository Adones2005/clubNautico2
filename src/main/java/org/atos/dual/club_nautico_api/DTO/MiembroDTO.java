package org.atos.dual.club_nautico_api.DTO;

import lombok.Data;


@Data
public class MiembroDTO {
    private Long id;
    private PersonaDTO persona; // Asociación con PersonaDTO
}


