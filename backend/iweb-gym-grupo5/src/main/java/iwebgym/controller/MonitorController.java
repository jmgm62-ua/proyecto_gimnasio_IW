package iwebgym.controller;

import iwebgym.service.InstalacionService;
import iwebgym.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/monitores")
public class MonitorController {

    @Autowired
    private MonitorService monitorService;

    @GetMapping
    public ResponseEntity<?> getAllMonitores() {
        try {
            return ResponseEntity.ok(monitorService.findAllMonitores());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al obtener monitores: " + e.getMessage());
        }
    }
}
