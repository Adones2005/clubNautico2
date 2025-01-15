package org.atos.dual.club_nautico_api.DTO;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
public class BarcoDTO {
    private Long id;
    private String matricula;
    private String nombre;
    private Integer amarre;
    private double tarifa;
    private Long propietarioId;

    // Getters and Setters
}

