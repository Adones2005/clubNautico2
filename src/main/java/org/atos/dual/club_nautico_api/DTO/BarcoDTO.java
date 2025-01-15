package org.atos.dual.club_nautico_api.DTO;
import java.util.List;


public class BarcoDTO {

    private String matricula;
    private String nombre;
    private Integer amarre;
    private double tarifa;
    private Long propietarioId; // Referencia al ID del propietario
    private List<Long> viajesIds; // Lista de IDs de viajes asociados

    // Getters y Setters

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAmarre() {
        return amarre;
    }

    public void setAmarre(Integer amarre) {
        this.amarre = amarre;
    }

    public double getTarifa() {
        return tarifa;
    }

    public void setTarifa(double tarifa) {
        this.tarifa = tarifa;
    }

    public Long getPropietarioId() {
        return propietarioId;
    }

    public void setPropietarioId(Long propietarioId) {
        this.propietarioId = propietarioId;
    }

    public List<Long> getViajesIds() {
        return viajesIds;
    }

    public void setViajesIds(List<Long> viajesIds) {
        this.viajesIds = viajesIds;
    }
}

