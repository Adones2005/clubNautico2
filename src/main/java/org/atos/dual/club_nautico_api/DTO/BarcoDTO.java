package org.atos.dual.club_nautico_api.DTO;
import lombok.Data;


@Data
public class BarcoDTO {
    private Long id;
    private String matricula;
    private String nombre;
    private Integer amarre;
    private double tarifa;
    private MiembroDTO propietario;

}

