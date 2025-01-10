package iwebgym.controller;

import iwebgym.dto.*;
import iwebgym.model.Actividad;
import iwebgym.model.User;
import iwebgym.service.ActividadesService;
import iwebgym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ActividadesService actividadesService;

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

    @GetMapping("/getAllTareas")
    public ResponseEntity<?> getAllTareas() {
        ArrayList<ActividadData> actividades = actividadesService.findAllActividades();

        if (actividades == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay actividades");
        } else {
            return ResponseEntity.ok(actividades);
        }
    }
}
