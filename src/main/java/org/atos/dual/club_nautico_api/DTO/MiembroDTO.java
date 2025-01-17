package org.atos.dual.club_nautico_api.DTO;

import lombok.Data;
import org.atos.dual.club_nautico_api.Model.Persona;

import java.util.List;

@Data
public class MiembroDTO {
    private PersonaDTO personaDTO;
    private List<BarcoDTO> barcosDTO;
}
