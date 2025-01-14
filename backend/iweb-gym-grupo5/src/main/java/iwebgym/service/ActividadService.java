package iwebgym.service;

import iwebgym.model.Actividad;
import iwebgym.model.TipoActividad;
import iwebgym.model.Monitor;
import iwebgym.dto.ActividadData;
import iwebgym.dto.UserData;
import iwebgym.repository.TipoActividadRepository;
import iwebgym.repository.MonitorRepository;
import iwebgym.repository.ActividadRepository;
import iwebgym.repository.UserRepository;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ActividadService {

    private final TipoActividadRepository tipoActividadRepository;
    private final MonitorRepository monitorRepository;
    private final ActividadRepository actividadRepository;
    private final UserRepository usuarioRepository;

    @Autowired
    public ActividadService(TipoActividadRepository tipoActividadRepository,
                            MonitorRepository monitorRepository,
                            ActividadRepository actividadRepository,
                            UserRepository usuarioRepository) {
        this.tipoActividadRepository = tipoActividadRepository;
        this.monitorRepository = monitorRepository;
        this.actividadRepository = actividadRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Autowired
    public ActividadService(ActividadRepository actividadRepository, UsuarioRepository usuarioRepository) {
        this.actividadRepository = actividadRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<TipoActividad> getTiposActividad() {
        return tipoActividadRepository.findAll();
    }

    public List<Monitor> getMonitores() {
        return monitorRepository.findByActivo(true); // Solo monitores activos
    }

    public void addActividad(ActividadDTO actividadDTO) {
        Actividad actividad = new Actividad();
        actividad.setNombre(actividadDTO.getNombre());
        actividad.setDiaSemana(actividadDTO.getDiaSemana());
        actividad.setFechaInicio(actividadDTO.getFechaInicio());
        actividad.setFechaFin(actividadDTO.getFechaFin());
        actividad.setHoraInicio(actividadDTO.getHoraInicio());
        actividad.setHoraFin(actividadDTO.getHoraFin());
        actividad.setTipoActividad(tipoActividadRepository.findById(actividadDTO.getTipoActividadId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de actividad no encontrado")));
        actividad.setMonitor(monitorRepository.findById(actividadDTO.getMonitorId())
                .orElseThrow(() -> new IllegalArgumentException("Monitor no encontrado")));
        actividadRepository.save(actividad);
    }

    public List<UserData> obtenerAsistentesPorActividad(Long actividadId) {
        // Verificar que la actividad existe
        Actividad actividad = actividadRepository.findById(actividadId)
                .orElseThrow(() -> new EntityNotFoundException("Actividad no encontrada"));

        // Obtener los usuarios que asisten a esta actividad
        List<Usuario> asistentes = usuarioRepository.findUsuariosPorActividadId(actividadId);

        // Mapear a DTO para enviar solo los datos necesarios
        return asistentes.stream()
                .map(usuario -> new UsuarioDTO(
                        usuario.getId(),
                        usuario.getName(),
                        usuario.getApellidos(),
                        usuario.getEmail(),
                        usuario.getDireccion(),
                        usuario.getTelefono(),
                        usuario.getFechaNacimiento()
                ))
                .collect(Collectors.toList());
    }
}