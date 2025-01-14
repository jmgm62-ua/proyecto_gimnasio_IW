package iwebgym.controller;

import iwebgym.dto.*;
import iwebgym.model.Actividad;
import iwebgym.model.Socio;
import iwebgym.model.User;
import iwebgym.service.ActividadesService;
import iwebgym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @GetMapping("/getTareasMonitor")
    public ResponseEntity<?> getTareasMonitor(@RequestParam String email) {
        ArrayList<ActividadData> actividades = actividadesService.findActividadesByMonitorEmail(email);

        if (actividades == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró el monitor con email: " + email);
        }

        if (actividades.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El monitor no tiene actividades asignadas");
        }

        return ResponseEntity.ok(actividades);
    }
  
    @PutMapping("/actualizar-cuota/{email}")
    public ResponseEntity<?> actualizarTipoCuota(@PathVariable String email, @RequestBody Map<String, String> body) {
        String nuevaCuota = body.get("tipoCuota");
        boolean actualizado = userService.actualizarTipoCuota(email, nuevaCuota);

        if (actualizado) {
            return ResponseEntity.ok().body("Tipo de cuota actualizado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo actualizar el tipo de cuota");
        }
    }

    @PostMapping("/registro-socio")
    public ResponseEntity<?> registroSocio(@RequestBody SocioRegistroRequestData request) {
        try {
            Socio nuevoSocio = userService.registrarNuevoSocio(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoSocio);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al registrar el socio: " + e.getMessage());
        }
    }

    @GetMapping("/getAllTareas/{type}")
    public ResponseEntity<?> getAllTareas(@PathVariable String type) {
        ArrayList<ActividadData> actividades = actividadesService.findAllActividadesTipo(type);

        if (actividades == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay actividades");
        } else {
            return ResponseEntity.ok(actividades);
        }
    }

    @GetMapping("/cargar_saldo/{referencia}/{saldo_a_cargar}/{email}")
    public ResponseEntity<?> cargarSaldo(
            @PathVariable String referencia,
            @PathVariable float saldo_a_cargar,
            @PathVariable String email
    ) {
        try {
            userService.cargar_saldo(referencia, saldo_a_cargar, email);
            return ResponseEntity.ok().body(
                    "<html><body><h3>Saldo correctamente cargado.</h3>" +
                            "<p>Pulsa <a href=\"http://localhost:5173/\">aquí</a> para volver a la página del gimnasio.</p></body></html>"
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "<html><body><h3>Error al cargar el saldo:</h3>" +
                            "<p>" + e.getMessage() + "</p>" +
                            "<p>Por favor, intenta nuevamente o contacta con soporte.</p></body></html>"
            );
        }
    }


    @GetMapping("/registrar_referencia/{referencia}")
    public ResponseEntity<?> registrar_referencia(
            @PathVariable String referencia
    ) {
        try {
            userService.registrarNuevaReferencia(referencia);
        }catch (Exception e){
            return ResponseEntity.ok("Referencia ya existente, no se registra");
        }

        return ResponseEntity.ok("Referencia registrada");
    }

}
