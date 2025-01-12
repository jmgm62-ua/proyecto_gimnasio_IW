package iwebgym.controller;

import iwebgym.service.InformesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/informes")
public class InformesController {

    @Autowired
    private InformesService informesService;

    @GetMapping("/{tipoInforme}")
    public ResponseEntity<Map<String, Object>> getInforme(
            @PathVariable String tipoInforme,
            @RequestParam String tipo,
            @RequestParam(required = false) Integer mes,
            @RequestParam Integer año) {

        // Validate mes parameter based on tipo
        if ("mensual".equals(tipo) && mes == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "El parámetro 'mes' es requerido para el tipo 'mensual'."));
        }

        Map<String, Object> informe;

        switch (tipoInforme) {
            case "asistencia":
                informe = informesService.getInformeAsistencia(tipo, mes, año);
                break;
            case "socios":
                informe = informesService.getInformeSocios(tipo, mes, año);
                break;
            case "altas":
                informe = informesService.getInformeAltas(tipo, mes, año);
                break;
            case "ingresos":
                informe = informesService.getInformeIngresos(tipo, mes, año);
                break;
            default:
                return ResponseEntity.badRequest().body(Map.of("error", "Tipo de informe no válido."));
        }

        return ResponseEntity.ok(informe);
    }
}