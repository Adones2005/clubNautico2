package org.atos.dual.club_nautico_api.Controller;

import jakarta.validation.Valid;
import org.atos.dual.club_nautico_api.DTO.AuthRequestDTO;
import org.atos.dual.club_nautico_api.DTO.AuthResponseDTO;
import org.atos.dual.club_nautico_api.DTO.PersonaDTO;
import org.atos.dual.club_nautico_api.DTO.RegisterRequestDTO;
import org.atos.dual.club_nautico_api.Model.Persona;
import org.atos.dual.club_nautico_api.Model.Role;
import org.atos.dual.club_nautico_api.Repository.PersonaRepository;
import org.atos.dual.club_nautico_api.Repository.RoleRepository;
import org.atos.dual.club_nautico_api.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

@Autowired
private AuthenticationManager authenticationManager;

@Autowired
private PasswordEncoder passwordEncoder;

@Autowired
private PersonaRepository personaRepository;

@Autowired
private RoleRepository roleRepository;

@Autowired
private JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDTO> authenticate(@Valid @RequestBody AuthRequestDTO authRequest) {
        try {
            // Validar datos de entrada (opcional si no usas validación adicional en DTO)
            if (authRequest.getUsername() == null || authRequest.getPassword() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponseDTO(null, "El nombre de usuario y la contraseña son obligatorios."));
            }

            // Intentar autenticar al usuario con las credenciales proporcionadas
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            // Obtiene el nombre de usuario autenticado
            String username = authentication.getName();
            // Extrae los roles del usuario autenticado desde las autoridades asignadas
            List<String> roles = authentication.getAuthorities().stream()
                    .map(authority -> authority.getAuthority()) // Convierte cada autoridad en su representación de texto
                    .toList();

            Persona user = personaRepository.findByUsername(username).orElseThrow();
            // Genera un token JWT para el usuario autenticado, incluyendo sus roles
            String token = jwtUtil.generateToken(username, roles, user.getId());
            // Retorna una respuesta con el token JWT y un mensaje de éxito
            return ResponseEntity.ok(new AuthResponseDTO(token, "Authentication successful"));
        } catch (BadCredentialsException e) {
            // Manejo de credenciales inválidas
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponseDTO(null, "Credenciales inválidas. Por favor, verifica tus datos."));
        } catch (Exception e) {
            // Manejo de cualquier otro error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponseDTO(null, "Ocurrió un error inesperado. Por favor, inténtalo de nuevo más tarde."));
        }
    }
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody PersonaDTO registerRequest) {
        try {
            // Validar si el usuario ya existe
            if (personaRepository.existsByUsername(registerRequest.getUsername())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponseDTO(null, "El nombre de usuario ya está en uso."));
            }

            // Crear nueva Persona
            Persona persona = new Persona(registerRequest);

            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName("ROLE_USER").orElseThrow());
            persona.setRoles(roles);

            // Encriptar la contraseña con el PasswordEncoder
            String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
            persona.setPassword(encodedPassword);  // Asignar la contraseña encriptada

            persona = personaRepository.save(persona); // Guardar persona en BD


            // Generar token JWT
            String token = jwtUtil.generateToken(persona.getUsername(), List.of("ROLE_USER"),persona.getId());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new AuthResponseDTO(token, "Usuario registrado exitosamente."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponseDTO(null, "Ocurrió un error al registrar el usuario."));
        }
    }



    /**
     * Maneja excepciones no controladas que puedan ocurrir en el controlador.
     * @param e La excepción lanzada.
     * @return Una respuesta HTTP con el mensaje de error y el estado HTTP correspondiente.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<AuthResponseDTO> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new AuthResponseDTO(null, "Ocurrió un error inesperado: " + e.getMessage()));
    }


}
