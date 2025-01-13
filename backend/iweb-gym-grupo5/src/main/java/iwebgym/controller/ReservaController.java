package iwebgym.controller;

import iwebgym.dto.ActividadData;
import iwebgym.dto.ReservaData;
import iwebgym.dto.StringIntTuple;
import iwebgym.service.ActividadesService;
import iwebgym.service.ReservaService;
import iwebgym.service.TipoActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
