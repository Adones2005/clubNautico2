package org.atos.dual.club_nautico_api.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
import java.util.Set;

@Entity
@Data
@ToString(exclude = "roles") // Excluye roles para evitar problemas de recursión en el toString.
@EqualsAndHashCode(exclude = "roles") // Excluye roles para evitar recursión en equals y hashCode.
@EntityListeners(AuditingEntityListener.class) // Habilita las anotaciones de auditoría.
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(max = 50)
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @NotEmpty
    @Size(min = 8)
    @Column(name = "password", nullable = false)
    private String password;



    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellidos;

    private String telefono;
    private String direccion;
    private Boolean esPatron;

    @OneToMany(mappedBy = "organizador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Viaje> viajes;


}