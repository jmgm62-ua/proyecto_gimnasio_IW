package iwebgym.controller;

import iwebgym.authentication.ManagerUserSession;
import iwebgym.dto.ActividadData;
import iwebgym.service.ActividadService;
import iwebgym.model.TipoActividad;
import iwebgym.model.monitor;
import iwebgym.service.ActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/actividad")
public class ActividadController {

    private final ActividadService actividadService;

    public ActividadController(ActividadService actividadService) {
        this.actividadService = actividadService;
    }

    @GetMapping("/get-tipos-actividad")
    public List<TipoActividad> getTiposActividad() {
        return actividadService.getTiposActividad();
    }

    @GetMapping("/get-monitores")
    public List<Monitor> getMonitores() {
        return actividadService.getMonitores();
    }

    @PostMapping("/add-actividad")
    public ResponseEntity<String> addActividad(@RequestBody ActividadDTO actividadDTO) {
        actividadService.addActividad(actividadDTO);
        return ResponseEntity.ok("Actividad añadida con éxito");
    }

    // Endpoint para obtener los asistentes a una actividad específica
    @GetMapping("/{id}/asistentes")
    public ResponseEntity<List<UsuarioDTO>> getAsistentesPorActividad(@PathVariable Long id) {
        try {
            List<UsuarioDTO> asistentes = actividadService.obtenerAsistentesPorActividad(id);
            return ResponseEntity.ok(asistentes);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
