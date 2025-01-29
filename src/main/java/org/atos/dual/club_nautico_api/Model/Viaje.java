package org.atos.dual.club_nautico_api.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaHora;
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizador_id", foreignKey = @ForeignKey(name = "fk_viaje_organizador"))
    private Persona organizador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barco_id", foreignKey = @ForeignKey(name = "fk_viaje_barco"))
    private Barco barco;
}