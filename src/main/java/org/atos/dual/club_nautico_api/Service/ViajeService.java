package org.atos.dual.club_nautico_api.Service;

import org.atos.dual.club_nautico_api.DTO.ViajeDTO;
import org.atos.dual.club_nautico_api.Mappers.ViajeMapper;
import org.atos.dual.club_nautico_api.Model.Viaje;
import org.atos.dual.club_nautico_api.Repository.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ViajeService {
    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private ViajeMapper viajeMapper;

    public List<ViajeDTO> getAllViajes() {
        List<Viaje> viajes = viajeRepository.findAll();
        return viajes.stream()
                .map(viajeMapper::toDTO)
                .toList(); // Convierte las entidades a DTOs
    }

    public Optional<ViajeDTO> getViaje(Long id) {
        return viajeRepository.findById(id)
                .map(viajeMapper::toDTO); // Convierte la entidad encontrada a DTO
    }

    public ViajeDTO saveViaje(ViajeDTO viajeDTO) {
        Viaje viaje = viajeMapper.toEntity(viajeDTO); // Convierte el DTO a entidad
        Viaje savedViaje = viajeRepository.save(viaje);
        return viajeMapper.toDTO(savedViaje); // Convierte la entidad guardada a DTO
    }

    public void deleteViaje(Long id) {
        viajeRepository.deleteById(id);
    }
}
