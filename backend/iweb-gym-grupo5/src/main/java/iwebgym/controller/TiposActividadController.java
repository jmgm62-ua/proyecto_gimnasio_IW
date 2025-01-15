package iwebgym.controller;

import iwebgym.service.TipoActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tipos-actividad")
public class TiposActividadController {

    @Autowired
    private TipoActividadService tipoActividadService;

    @GetMapping
    public ResponseEntity<?> getAllTiposActividad() {
        try {
            return ResponseEntity.ok(tipoActividadService.findAllTiposActividad());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al obtener tipos de actividad: " + e.getMessage());
        }
    }
}
