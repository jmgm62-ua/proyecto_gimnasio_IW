package iwebgym.controller;

import iwebgym.dto.ReservaData;
import iwebgym.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {
    @Autowired
    private ReservaService reservaService;

    @GetMapping("/historicas/{email}")
    public ResponseEntity<List<ReservaData>> getReservasHistoricas(@PathVariable String email) {
        return ResponseEntity.ok(reservaService.getReservasHistoricas(email));
    }

    @GetMapping("/pendientes/{email}")
    public ResponseEntity<List<ReservaData>> getReservasPendientes(@PathVariable String email) {
        return ResponseEntity.ok(reservaService.getReservasPendientes(email));
    }
}
