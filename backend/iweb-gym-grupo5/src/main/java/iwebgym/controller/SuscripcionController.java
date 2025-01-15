package iwebgym.controller;

import iwebgym.dto.SuscripcionDTO;
import iwebgym.service.SuscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/suscripciones")
public class SuscripcionController {

    @Autowired
    private SuscripcionService suscripcionService;

    @GetMapping
    public ResponseEntity<List<SuscripcionDTO>> getAllSuscripciones() {
        return ResponseEntity.ok(suscripcionService.getAllSuscripciones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuscripcionDTO> getSuscripcionById(@PathVariable Long id) {
        return ResponseEntity.ok(suscripcionService.getSuscripcionById(id));
    }

    @PostMapping
    public ResponseEntity<SuscripcionDTO> createSuscripcion(@Valid @RequestBody SuscripcionDTO suscripcionDTO) {
        return new ResponseEntity<>(suscripcionService.createSuscripcion(suscripcionDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSuscripcion(@PathVariable Long id) {
        suscripcionService.deleteSuscripcion(id);
        return ResponseEntity.noContent().build();
    }
}
