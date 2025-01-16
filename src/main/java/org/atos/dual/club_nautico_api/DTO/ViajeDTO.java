package org.atos.dual.club_nautico_api.DTO;

import lombok.Data;

@Data
public class ViajeDTO {
    private Long id;
    private String descripcion;
    private BarcoDTO barco;
    private PersonaDTO organizador;
}