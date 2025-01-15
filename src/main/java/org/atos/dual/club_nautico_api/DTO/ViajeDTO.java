package org.atos.dual.club_nautico_api.DTO;

import lombok.Data;

@Data
public class ViajeDTO {
    private Long id;
    private String descripcion;
    private Long barcoId;
    private Long organizadorId;
}