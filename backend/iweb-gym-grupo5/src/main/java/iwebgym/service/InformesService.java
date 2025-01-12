package iwebgym.service;

import iwebgym.model.Ingreso;
import iwebgym.model.Reserva;
import iwebgym.model.Socio;
import iwebgym.repository.ReservaRepository;
import iwebgym.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InformesService {
    @Autowired
    private ReservaRepository reservasRepository;

    @Autowired
    private SocioRepository sociosRepository;

    @Autowired
    private IngresoService ingresoService;

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

        // Obtener todas las reservas del período
        List<Reserva> reservas = reservasRepository.findByFechaBetween(fechaInicio, fechaFin);

        // Agrupar reservas por actividad
        Map<String, Long> reservasPorActividad = reservas.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getActividad().getNombre(),
                        Collectors.counting()
                ));

        // Obtener socios únicos que asistieron
        Set<Socio> sociosAsistentes = reservas.stream()
                .map(Reserva::getSocio)
                .collect(Collectors.toSet());

        // Detalles de los asistentes
        List<Map<String, Object>> detallesAsistentes = sociosAsistentes.stream()
                .map(socio -> {
                    Map<String, Object> detalleSocio = new HashMap<>();
                    detalleSocio.put("id", socio.getId());
                    detalleSocio.put("nombre", socio.getName());
                    detalleSocio.put("email", socio.getEmail());
                    detalleSocio.put("numeroReservas",
                            reservas.stream().filter(r -> r.getSocio().getId().equals(socio.getId())).count());
                    return detalleSocio;
                })
                .collect(Collectors.toList());

        informe.put("periodo", tipo);
        informe.put("fechaInicio", fechaInicio);
        informe.put("fechaFin", fechaFin);
        informe.put("totalAsistentes", (long) sociosAsistentes.size());
        informe.put("reservasPorActividad", reservasPorActividad);
        informe.put("detallesAsistentes", detallesAsistentes);

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

        List<Socio> sociosActivos = sociosRepository.findActiveSociosAtDate(fecha);

        // Agrupar socios por tipo de cuota
        Map<String, Long> sociosPorCuota = sociosActivos.stream()
                .collect(Collectors.groupingBy(
                        Socio::getTipoCuota,
                        Collectors.counting()
                ));

        // Detalles de los socios
        List<Map<String, Object>> detallesSocios = sociosActivos.stream()
                .map(socio -> {
                    Map<String, Object> detalleSocio = new HashMap<>();
                    detalleSocio.put("id", socio.getId());
                    detalleSocio.put("nombre", socio.getName());
                    detalleSocio.put("email", socio.getEmail());
                    detalleSocio.put("fechaAlta", socio.getFechaAlta());
                    detalleSocio.put("tipoCuota", socio.getTipoCuota());
                    detalleSocio.put("saldo", socio.getSaldo());
                    return detalleSocio;
                })
                .collect(Collectors.toList());

        informe.put("periodo", tipo);
        informe.put("fecha", fecha);
        informe.put("totalSocios", (long) sociosActivos.size());
        informe.put("sociosPorCuota", sociosPorCuota);
        informe.put("detallesSocios", detallesSocios);

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

        List<Socio> nuevosAltas = sociosRepository.findNewSociosBetweenDates(fechaInicio, fechaFin);

        // Detalles de las nuevas altas
        List<Map<String, Object>> detallesAltas = nuevosAltas.stream()
                .map(socio -> {
                    Map<String, Object> detalleSocio = new HashMap<>();
                    detalleSocio.put("id", socio.getId());
                    detalleSocio.put("nombre", socio.getName());
                    detalleSocio.put("email", socio.getEmail());
                    detalleSocio.put("fechaAlta", socio.getFechaAlta());
                    detalleSocio.put("tipoCuota", socio.getTipoCuota());
                    detalleSocio.put("telefono", socio.getTelefono());
                    return detalleSocio;
                })
                .collect(Collectors.toList());

        informe.put("periodo", tipo);
        informe.put("fechaInicio", fechaInicio);
        informe.put("fechaFin", fechaFin);
        informe.put("totalNuevasAltas", (long) nuevosAltas.size());
        informe.put("detallesAltas", detallesAltas);

        return informe;
    }

    public Map<String, Object> getInformeIngresos(String tipo, Integer mes, Integer año) {
        Map<String, Object> informe = new HashMap<>();
        Date fechaInicio;
        Date fechaFin;

        // Configurar fechas según el tipo de informe
        Calendar cal = Calendar.getInstance();
        if (tipo.equals("mensual")) {
            cal.set(año, mes - 1, 1, 0, 0, 0);
            fechaInicio = cal.getTime();
            cal.add(Calendar.MONTH, 1);
            cal.add(Calendar.SECOND, -1);
            fechaFin = cal.getTime();
        } else {
            cal.set(año, 0, 1, 0, 0, 0);
            fechaInicio = cal.getTime();
            cal.add(Calendar.YEAR, 1);
            cal.add(Calendar.SECOND, -1);
            fechaFin = cal.getTime();
        }

        List<Ingreso> ingresos = ingresoService.getIngresosBetweenFechas(fechaInicio, fechaFin);
        BigDecimal totalIngresos = ingresoService.getTotalIngresosBetweenFechas(fechaInicio, fechaFin);

        informe.put("periodo", tipo);
        informe.put("fechaInicio", fechaInicio);
        informe.put("fechaFin", fechaFin);
        informe.put("ingresos", ingresos);
        informe.put("totalIngresos", totalIngresos);

        return informe;
    }
}