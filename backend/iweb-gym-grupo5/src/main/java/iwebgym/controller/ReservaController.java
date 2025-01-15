package iwebgym.controller;

import iwebgym.dto.*;
import iwebgym.model.Reserva;
import iwebgym.model.Socio;
import iwebgym.service.ActividadesService;
import iwebgym.service.ReservaService;
import iwebgym.service.TipoActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {
    @Autowired
    private ReservaService reservaService;
    @Autowired
    private TipoActividadService tipoActividadService;
    @Autowired
    private ActividadesService actividadesService;

    @GetMapping("/historicas/{email}")
    public ResponseEntity<List<ReservaData>> getReservasHistoricas(@PathVariable String email) {
        return ResponseEntity.ok(reservaService.getReservasHistoricas(email));
    }

    @GetMapping("/pendientes/{email}")
    public ResponseEntity<List<ReservaData>> getReservasPendientes(@PathVariable String email) {
        return ResponseEntity.ok(reservaService.getReservasPendientes(email));
    }

    @GetMapping("/tipos_actividades")
    public ResponseEntity<ArrayList<String>> getTiposDeActividades() {
        ArrayList<String> tipos = tipoActividadService.findAllTipos();
        return ResponseEntity.ok(tipos);
    }

    @GetMapping("/get_data_of_tipo/{id_actividad}")
    public ResponseEntity<StringIntTuple> getReservasHistoricas(@PathVariable Long id_actividad) {
        return ResponseEntity.ok(tipoActividadService.findNameAndPrice(id_actividad));
    }

    @GetMapping("/actividad/{id_actividad}")
    public ResponseEntity<ActividadData> getTareaByID(@PathVariable Long id_actividad) {
        ActividadData actividadData = actividadesService.finByID(id_actividad);
        return ResponseEntity.ok(actividadData);
    }

    @PostMapping("/realiza_reserva")
    public ResponseEntity<String> realizarReserva(@RequestBody ReservaRequest reservaRequest) {
        try {

            reservaService.crearReserva(reservaRequest);
            return ResponseEntity.ok("Reserva realizada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al realizar la reserva: " + e.getMessage());
        }
    }

    // En función de http://localhost:8080/actividades/${occurrence.activityId}/asistentes?date=${occurrence.start.toISOString().split('T')[0]}
    @GetMapping("/actividades/{id_actividad}/asistentes")
    public ResponseEntity<List<SocioData>> getAsistentes(
            @PathVariable Long id_actividad,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<SocioData> asistentes = reservaService.getAsistentes(id_actividad, date.toString());
        return ResponseEntity.ok(asistentes);
    }
}
