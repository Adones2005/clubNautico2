package org.atos.dual.club_nautico_api.Model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Barco{



    @Column (nullable = false)
    private String matricula;

    @Column (nullable = false)
    private String nombre;


    private Integer amarre;


    private double tarifa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "propietario", nullable = false , insertable=false, updatable=false)
    private Miembro propietario;

    @OneToMany(mappedBy = "barco", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Viaje> viajes;
}
