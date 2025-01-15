package org.atos.dual.club_nautico_api.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Viaje extends DomainEntity {


    private LocalDateTime fecha_hora;
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizador", nullable = false)
    private Persona organizador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barco", nullable = false)
    private Barco barco;
}
