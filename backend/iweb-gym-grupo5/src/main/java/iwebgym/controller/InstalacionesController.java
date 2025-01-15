package iwebgym.controller;

import iwebgym.service.InstalacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/instalaciones")
public class InstalacionesController {

    @Autowired
    private InstalacionService instalacionService;

    @GetMapping
    public ResponseEntity<?> getAllInstalaciones() {
        try {
            return ResponseEntity.ok(instalacionService.findAllInstalaciones());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al obtener instalaciones: " + e.getMessage());
        }
    }
}
