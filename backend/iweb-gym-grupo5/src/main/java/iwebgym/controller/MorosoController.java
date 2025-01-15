package iwebgym.controller;

import iwebgym.dto.MorosoDTO;
import iwebgym.service.MorosoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/morosos")
@CrossOrigin(origins = "http://localhost:5173")
public class MorosoController {

    @Autowired
    private MorosoService morosoService;

    @GetMapping
    public ResponseEntity<List<MorosoDTO>> getAllMorosos() {
        List<MorosoDTO> morosos = morosoService.getAllMorososWithDetails();
        return ResponseEntity.ok(morosos);
    }

    @PutMapping("/{socioId}/desactivar")
    public ResponseEntity<Void> desactivarSocioMoroso(@PathVariable Long socioId) {
        morosoService.desactivarSocioMoroso(socioId);
        return ResponseEntity.ok().build();
    }
}