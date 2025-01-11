package iwebgym.controller;

import iwebgym.dto.ActividadData;
import iwebgym.dto.SocioData;
import iwebgym.service.ActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monitor/actividades")
@CrossOrigin(origins = "http://localhost:5173")
public class ActividadController {
    @Autowired
    private ActividadService actividadService;

    @GetMapping("/{monitorEmail}/historicas")
    public ResponseEntity<List<ActividadData>> getClasesHistoricas(
            @PathVariable String monitorEmail,
            @RequestParam(required = false) String format) {
        return ResponseEntity.ok(actividadService.getClasesHistoricas(monitorEmail, format));
    }

    @GetMapping("/{monitorEmail}/pendientes")
    public ResponseEntity<List<ActividadData>> getClasesPendientes(
            @PathVariable String monitorEmail,
            @RequestParam(required = false) String format) {
        return ResponseEntity.ok(actividadService.getClasesPendientes(monitorEmail, format));
    }

    @GetMapping("/{actividadId}/asistentes")
    public ResponseEntity<List<SocioData>> getAsistentesClase(@PathVariable Long actividadId) {
        return ResponseEntity.ok(actividadService.getAsistentesClase(actividadId));
    }
}
