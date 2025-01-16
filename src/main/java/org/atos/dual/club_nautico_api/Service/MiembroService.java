package org.atos.dual.club_nautico_api.Service;

import org.atos.dual.club_nautico_api.DTO.MiembroDTO;
import org.atos.dual.club_nautico_api.Mappers.MiembroMapper;
import org.atos.dual.club_nautico_api.Model.Miembro;
import org.atos.dual.club_nautico_api.Model.Persona;
import org.atos.dual.club_nautico_api.Repository.MiembroRepository;
import org.atos.dual.club_nautico_api.Repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MiembroService {

    @Autowired
    private MiembroRepository miembroRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private MiembroMapper miembroMapper;

    public List<MiembroDTO> getAllMiembros() {
        return miembroRepository.findAll().stream()
                .map(miembroMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<MiembroDTO> getMiembro(Long id) {
        return miembroRepository.findById(id)
                .map(miembroMapper::toDTO);
    }

    public MiembroDTO saveMiembro(MiembroDTO miembroDTO) {
        // Verificar si la persona existe por DNI
        Persona persona = personaRepository.findByDNI(miembroDTO.getDNI());
        if (persona == null) {
            // Retornar null o lanzar una excepción si no existe la persona con el DNI
            return null;
        }

        // Verificar si la persona ya es miembro
        Miembro miembro = miembroRepository.findById(persona.getId()).orElse(null);

        if (miembro == null) {
            // Si la persona no es miembro, crear un nuevo miembro
            miembro = new Miembro();
            miembro.setId(null);  // Dejar que el sistema genere un nuevo ID
        }

        // Asignar los datos de la persona al miembro
        miembro.setNombre(persona.getNombre());
        miembro.setApellidos(persona.getApellidos());
        miembro.setDNI(persona.getDNI());
        miembro.setTelefono(persona.getTelefono());
        miembro.setDireccion(persona.getDireccion());
        miembro.setEsPatron(persona.getEsPatron());

        // Guardar o actualizar el miembro en la base de datos
        Miembro savedMiembro = miembroRepository.saveAndFlush(miembro);

        // Retornar el MiembroDTO mapeado desde la entidad guardada
        return miembroMapper.toDTO(savedMiembro);
    }

    public void deleteMiembro(Long id) {
        miembroRepository.deleteById(id);
    }
}

