package iwebgym.controller;

import iwebgym.dto.MonitorData;
import iwebgym.dto.SocioData;
import iwebgym.dto.UserData;
import iwebgym.dto.WebMasterData;
import iwebgym.model.User;
import iwebgym.service.UserService;
import iwebgym.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private SocioRepository socioRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllusers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }


    // Obtener un usuario por email
    @GetMapping("/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        UserData userData = userService.findByEmail(email);

        if (userData == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        } else {
            return ResponseEntity.ok(userData);
        }
    }

    // Obtener un socio por email
    @GetMapping("/find_socio/{email}")
    public ResponseEntity<?> getSocioByEmail(@PathVariable String email) {
        SocioData userData = userService.findSocioByEmail(email);

        if (userData == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        } else {
            return ResponseEntity.ok(userData);
        }
    }

    // Obtener un monitor por email
    @GetMapping("/find_monitor/{email}")
    public ResponseEntity<?> getMonitorByEmail(@PathVariable String email) {
        MonitorData userData = userService.findMonitorByEmail(email);

        if (userData == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        } else {
            return ResponseEntity.ok(userData);
        }
    }

    // Obtener un socio por email
    @GetMapping("/find_webmaster/{email}")
    public ResponseEntity<?> getWebMasterByEmail(@PathVariable String email) {
        WebMasterData userData = userService.findWebMasterByEmail(email);

        if (userData == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        } else {
            return ResponseEntity.ok(userData);
        }
    }

    // Lista de usuario pendientes de activación por el webmaster
    @GetMapping("/inactivos")
    public ResponseEntity<List<UserData>> getInactiveUsers() {
        List<UserData> inactivos = userService.findInactiveUsers();
        return ResponseEntity.ok(inactivos);
    }

    // Soocios nuevos pendientes de aprobacion
    @GetMapping("/nuevos")
    public ResponseEntity<List<Usuario>> getNewMemberRequests() {
        List<Usuario> nuevosSocios = userService.getNewMemberRequests();
        return ResponseEntity.ok(nuevosSocios);
    }

    // Se aprueba un socio nuevo por el webmaster
    @PutMapping("/{id}/aprobar")
    public ResponseEntity<Void> approveUser(@PathVariable Long id) {
        userService.approveUser(id);
        return ResponseEntity.ok().build();
    }

    // Se elimina un usuario nuevo que el webmaster ha rechazado
    @DeleteMapping("/{id}/rechazar")
    public ResponseEntity<Void> rejectUser(@PathVariable Long id) {
        userService.rejectUser(id);
        return ResponseEntity.noContent().build();
    }

    // Lista de usuarios activos
    @GetMapping("/activos")
    public ResponseEntity<List<Socio>> getActiveMembers() {
        List<Socio> activeMembers = socioRepository.findByActivoTrue();
        return ResponseEntity.ok(activeMembers);
    }


    @PutMapping("/{id}/desactivar")
    public ResponseEntity<String> deactivateUser(@PathVariable Long id) {
        try {
            boolean success = userService.deactivateUser(id);
            if (success) {
                return ResponseEntity.ok("Usuario desactivado con éxito.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al desactivar el usuario.");
        }
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<String> activateUser(@PathVariable Long id) {
        try {
            boolean success = userService.activateUser(id);
            if (success) {
                return ResponseEntity.ok("Usuario activado con éxito.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al activar el usuario.");
        }
    }
}
