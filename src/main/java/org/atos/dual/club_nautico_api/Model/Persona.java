package org.atos.dual.club_nautico_api.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Persona extends DomainEntity {

    private String nombre;
    private String apellidos;
    private String telefono;
    private String direccion;
    private Boolean esPatron;

    @OneToMany(mappedBy = "organizador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Viaje> viajes;
}
