package org.atos.dual.club_nautico_api.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ViajeDTO {
    private Long id;
    private String descripcion;
    private LocalDateTime fechaHora;
    private BarcoDTO barco;
    private PersonaDTO organizador;
}