package org.atos.dual.club_nautico_api.Mappers;

import org.atos.dual.club_nautico_api.DTO.BarcoDTO;
import org.atos.dual.club_nautico_api.Model.Barco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

// Mapper para Barco
@Component
public class BarcoMapper {

    @Autowired
    @Lazy
    private MiembroMapper miembroMapper;

    public BarcoDTO toDTO(Barco barco) {
        BarcoDTO dto = new BarcoDTO();
        dto.setId(barco.getId());
        dto.setMatricula(barco.getMatricula());
        dto.setNombre(barco.getNombre());
        dto.setAmarre(barco.getAmarre());
        dto.setTarifa(barco.getTarifa());
        dto.setPropietario(miembroMapper.toDTO(barco.getPropietario()));
        return dto;
    }

    public Barco toEntity(BarcoDTO dto) {
        Barco barco = new Barco();
        barco.setId(dto.getId());
        barco.setMatricula(dto.getMatricula());
        barco.setNombre(dto.getNombre());
        barco.setAmarre(dto.getAmarre());
        barco.setTarifa(dto.getTarifa());
        barco.setPropietario(miembroMapper.toEntity(dto.getPropietario()));
        return barco;
    }
}