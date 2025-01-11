package iwebgym.service;

import iwebgym.repository.ReservaRepository;
import iwebgym.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class InformesService {
    @Autowired
    private ReservaRepository reservasRepository;

    @Autowired
    private SocioRepository sociosRepository;

    public Map<String, Object> getInformeAsistencia(String tipo, Integer mes, Integer año) {
        Map<String, Object> informe = new HashMap<>();
        Date fechaInicio;
        Date fechaFin;

        // Configurar fechas según el tipo de informe
        if (tipo.equals("mensual")) {
            Calendar cal = Calendar.getInstance();
            cal.set(año, mes - 1, 1, 0, 0, 0);
            fechaInicio = cal.getTime();

            cal.add(Calendar.MONTH, 1);
            cal.add(Calendar.SECOND, -1);
            fechaFin = cal.getTime();
        } else {
            Calendar cal = Calendar.getInstance();
            cal.set(año, 0, 1, 0, 0, 0);
            fechaInicio = cal.getTime();

            cal.add(Calendar.YEAR, 1);
            cal.add(Calendar.SECOND, -1);
            fechaFin = cal.getTime();
        }

        Long totalAsistentes = reservasRepository.countDistinctSociosByFechaBetween(
                fechaInicio,
                fechaFin
        );

        informe.put("periodo", tipo);
        informe.put("fechaInicio", fechaInicio);
        informe.put("fechaFin", fechaFin);
        informe.put("totalAsistentes", totalAsistentes);

        return informe;
    }

    public Map<String, Object> getInformeSocios(String tipo, Integer mes, Integer año) {
        Map<String, Object> informe = new HashMap<>();
        String fecha;

        // Configurar fecha para la consulta
        if (tipo.equals("mensual")) {
            fecha = String.format("%d-%02d-%02d", año, mes,
                    YearMonth.of(año, mes).lengthOfMonth());
        } else {
            fecha = String.format("%d-12-31", año);
        }

        Long totalSocios = sociosRepository.countActiveSociosAtDate(fecha);

        informe.put("periodo", tipo);
        informe.put("fecha", fecha);
        informe.put("totalSocios", totalSocios);

        return informe;
    }

    public Map<String, Object> getInformeAltas(String tipo, Integer mes, Integer año) {
        Map<String, Object> informe = new HashMap<>();
        String fechaInicio;
        String fechaFin;

        // Configurar fechas para la consulta
        if (tipo.equals("mensual")) {
            fechaInicio = String.format("%d-%02d-01", año, mes);
            fechaFin = String.format("%d-%02d-%02d", año, mes,
                    YearMonth.of(año, mes).lengthOfMonth());
        } else {
            fechaInicio = String.format("%d-01-01", año);
            fechaFin = String.format("%d-12-31", año);
        }

        Long nuevasAltas = sociosRepository.countNewSociosBetweenDates(
                fechaInicio,
                fechaFin
        );

        informe.put("periodo", tipo);
        informe.put("fechaInicio", fechaInicio);
        informe.put("fechaFin", fechaFin);
        informe.put("nuevasAltas", nuevasAltas);

        return informe;
    }
}