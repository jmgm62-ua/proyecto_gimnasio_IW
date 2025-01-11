package iwebgym.service;


import iwebgym.dto.ActividadData;
import iwebgym.dto.SocioData;
import iwebgym.model.Actividad;
import iwebgym.model.Monitor;
import iwebgym.model.Reserva;
import iwebgym.repository.ActividadRepository;
import iwebgym.repository.MonitorRepository;
import iwebgym.repository.ReservaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActividadService {
    @Autowired
    private ActividadRepository actividadRepository;
    @Autowired
    private MonitorRepository monitorRepository;
    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<ActividadData> getClasesHistoricas(String monitorEmail, String format) {
        Monitor monitor = monitorRepository.findByEmail(monitorEmail)
                .orElseThrow();

        LocalDateTime now = LocalDateTime.now();
        List<Actividad> actividades = actividadRepository.findByMonitorAndFechaFinBefore(monitorEmail, now);

        return formatActividades(actividades, format);
    }

    public List<ActividadData> getClasesPendientes(String monitorEmail, String format) {
        Monitor monitor = monitorRepository.findByEmail(monitorEmail)
                .orElseThrow();

        LocalDateTime now = LocalDateTime.now();
        List<Actividad> actividades = actividadRepository.findByMonitorAndFechaFinAfter(monitorEmail, now);

        return formatActividades(actividades, format);
    }

    public List<SocioData> getAsistentesClase(Long actividadId) {
        List<Reserva> reservas = reservaRepository.findByActividadId(actividadId);
        return reservas.stream()
                .map(reserva -> modelMapper.map(reserva.getSocio(), SocioData.class))
                .collect(Collectors.toList());
    }

    private List<ActividadData> formatActividades(List<Actividad> actividades, String format) {
        if ("calendar".equalsIgnoreCase(format)) {
            return actividades.stream()
                    .map(this::mapToCalendarDTO)
                    .collect(Collectors.toList());
        }
        return actividades.stream()
                .map(this::mapToListDTO)
                .collect(Collectors.toList());
    }

    private ActividadData mapToListDTO(Actividad actividad) {
        ActividadData dto = modelMapper.map(actividad, ActividadData.class);
        dto.setNumAsistentes(reservaRepository.countByActividadId(actividad.getId()));
        return dto;
    }

    private ActividadData mapToCalendarDTO(Actividad actividad) {
        ActividadData dto = mapToListDTO(actividad);
        // Add calendar-specific fields
        dto.setStart(actividad.getFechaInicio());
        dto.setEnd(actividad.getFechaFin());
        return dto;
    }
}