package org.atos.dual.club_nautico_api.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellidos;

    private String telefono;
    private String direccion;
    private Boolean esPatron;

    @OneToMany(mappedBy = "organizador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Viaje> viajes;

    @OneToOne(mappedBy = "persona",orphanRemoval = true,cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Miembro miembro;
}
