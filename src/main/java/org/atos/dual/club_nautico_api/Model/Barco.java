package org.atos.dual.club_nautico_api.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Barco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String matricula;

    @Column(nullable = false)
    private String nombre;

    private Integer amarre;
    private double tarifa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "propietario_id", nullable = false)
    private Miembro propietario;

    @OneToMany(mappedBy = "barco", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Viaje> viajes;


}