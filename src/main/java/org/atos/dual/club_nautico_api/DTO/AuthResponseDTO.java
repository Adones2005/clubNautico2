package org.atos.dual.club_nautico_api.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String message;
}

