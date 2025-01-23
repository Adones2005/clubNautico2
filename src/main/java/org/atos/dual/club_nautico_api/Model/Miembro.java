package org.atos.dual.club_nautico_api.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Miembro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "propietario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Barco> barcos;

    @OneToOne
    @MapsId
    @JoinColumn(name = "persona_id", referencedColumnName = "id", nullable = false, unique = true)
    private Persona persona;
}
