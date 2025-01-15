package iwebgym.controller;

import iwebgym.dto.ActividadData;
import iwebgym.service.ActividadesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/actividades")
public class ActividadController {

    @Autowired
    private ActividadesService actividadesService;

    @GetMapping
    public ResponseEntity<?> getAllActividades() {
        try {
            return ResponseEntity.ok(actividadesService.findAllActividades());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener actividades: " + e.getMessage());
        }
    }

    @GetMapping("/monitor/{email}")
    public ResponseEntity<?> getActividadesByMonitor(@PathVariable String email) {
        try {
            return ResponseEntity.ok(actividadesService.findActividadesByMonitorEmail(email));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener actividades del monitor: " + e.getMessage());
        }
    }

    @GetMapping("/tipo/{type}")
    public ResponseEntity<?> getActividadesByTipo(@PathVariable String type) {
        try {
            return ResponseEntity.ok(actividadesService.findAllActividadesTipo(type));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener actividades por tipo: " + e.getMessage());
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearActividad(@RequestBody ActividadData actividadData) {
        try {
            ActividadData nuevaActividad = actividadesService.crearActividad(actividadData);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaActividad);
        } catch (RuntimeException e) {
            // Captura mensajes claros para la UI
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            // Manejo genérico de errores
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error inesperado al crear la actividad"));
        }
    }

}
