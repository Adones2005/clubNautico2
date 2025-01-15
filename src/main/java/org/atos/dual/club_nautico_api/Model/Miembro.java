package org.atos.dual.club_nautico_api.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Miembro extends Persona {
    @OneToMany(mappedBy = "propietario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Barco> barcos;


}