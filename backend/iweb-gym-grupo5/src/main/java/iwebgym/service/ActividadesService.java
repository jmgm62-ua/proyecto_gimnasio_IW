package iwebgym.service;

import iwebgym.authentication.ManagerUserSession;
import iwebgym.dto.*;
import iwebgym.model.*;
import iwebgym.repository.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActividadesService {

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TipoActividadService tipoActividadService;

    @Autowired
    private InstalacionRepository instalacionRepository;

    @Autowired
    private TipoActividadRepository tipoActividadRepository;

    @Transactional(readOnly = true)
    public ArrayList<ActividadData> findAllActividades() {
        List<Actividad> listaActividades = actividadRepository.findAll();
        ArrayList<ActividadData> actividadesData = new ArrayList<>();
        for (Actividad a : listaActividades) {
            ActividadData actividadData = new ActividadData();
            actividadData = modelMapper.map(a, ActividadData.class);
            actividadesData.add(actividadData);
        }
        return actividadesData;
    }

    public ArrayList<ActividadData> findActividadesByMonitorEmail(String email) {
        Optional<Monitor> monitor = monitorRepository.findByEmail(email);

        return monitor.map(value -> actividadRepository.findByMonitorId(value.getId())
                        .stream()
                        .map(actividad -> new ActividadData(
                                actividad.getId(),
                                actividad.getNombre(),
                                actividad.getDiaSemana(),
                                actividad.getHoraInicio(),
                                actividad.getHoraFin(),
                                actividad.getFechaInicio(),
                                actividad.getFechaFin(),
                                actividad.getTipoActividad().getNombre(),
                                actividad.getTipoActividad().getPrecioExtra()
                        ))
                        .collect(Collectors.toCollection(ArrayList::new)))
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public ArrayList<ActividadData> findAllActividadesTipo(String type) {
        List<Actividad> listaActividades = actividadRepository.findAll();
        ArrayList<ActividadData> actividadesData = new ArrayList<>();
        for (Actividad a : listaActividades) {
            ActividadData actividadData = new ActividadData();
            TipoActividad tipoActividad = a.getTipoActividad();
            int id_tipo = Integer.parseInt(tipoActividad.getNombre());
            if (type.equals(tipoActividad.ObtenerNombreById(id_tipo))){
                actividadData = modelMapper.map(a, ActividadData.class);
                actividadesData.add(actividadData);
            }
        }
        return actividadesData;
    }

    @Transactional(readOnly = true)
    public ActividadData finByID(Long id_buscada) {
        Optional<Actividad> actividad = actividadRepository.findById(id_buscada);

        ActividadData actividadData;

        actividadData = modelMapper.map(actividad.get(), ActividadData.class);
        StringIntTuple tuple = tipoActividadService.findNameAndPrice(actividadData.getId());
        actividadData.tipo_de_actividad = tuple.getNombre();
        actividadData.precio_extra_actividad =tuple.getPrice();

        return actividadData;
    }


    @Transactional
    public ActividadData crearActividad(ActividadData actividadData) {
        // Validar que la instalación existe
        Optional<Instalacion> instalacion = instalacionRepository.findById(actividadData.getInstalacionId());
        if (!instalacion.isPresent()) {
            throw new RuntimeException("La instalación seleccionada no existe");
        }

        // Validar que no haya conflicto de horarios
        if (existeConflictoHorario(actividadData)) {
            throw new RuntimeException("Ya existe una actividad en esa instalación para el horario seleccionado");
        }

        // Validar que el monitor existe
        Optional<Monitor> monitor = monitorRepository.findById(actividadData.getMonitorId());
        if (!monitor.isPresent()) {
            throw new RuntimeException("El monitor seleccionado no existe");
        }

        Long nextId = 1L; // Valor por defecto si no hay reservas
        List<Actividad> allActividades = actividadRepository.findAll();
        if (!allActividades.isEmpty()) {
            Long maxId = allActividades.stream()
                    .mapToLong(Actividad::getId)
                    .max()
                    .orElse(0L);
            nextId = maxId + 1;
        }

        // Crear la actividad
        Actividad actividad = modelMapper.map(actividadData, Actividad.class);
        actividad.setMonitor(monitor.get());
        actividad.setInstalacion(instalacion.get());
        actividad.setId(nextId);

        // Establecer el tipo de actividad
        Optional<TipoActividad> tipoActividad = tipoActividadRepository.findById(actividadData.getTipoActividadId());
        if (!tipoActividad.isPresent()) {
            throw new RuntimeException("El tipo de actividad seleccionado no existe");
        }
        actividad.setTipoActividad(tipoActividad.get());

        Actividad savedActividad = actividadRepository.save(actividad);
        return modelMapper.map(savedActividad, ActividadData.class);
    }

    private boolean existeConflictoHorario(ActividadData nuevaActividad) {
        List<Actividad> actividadesExistentes = actividadRepository
                .findByInstalacionIdAndDiaSemana(
                        nuevaActividad.getInstalacionId(),
                        nuevaActividad.getDiaSemana()
                );

        LocalTime nuevoInicio = LocalTime.parse(nuevaActividad.getHoraInicio());
        LocalTime nuevoFin = LocalTime.parse(nuevaActividad.getHoraFin());

        return actividadesExistentes.stream().anyMatch(actividad -> {
            LocalTime existenteInicio = LocalTime.parse(actividad.getHoraInicio());
            LocalTime existenteFin = LocalTime.parse(actividad.getHoraFin());

            return (nuevoInicio.isBefore(existenteFin) && nuevoInicio.isAfter(existenteInicio)) ||
                    (nuevoFin.isAfter(existenteInicio) && nuevoFin.isBefore(existenteFin)) ||
                    (nuevoInicio.isBefore(existenteInicio) && nuevoFin.isAfter(existenteFin)) ||
                    (nuevoInicio.equals(existenteInicio) || nuevoFin.equals(existenteFin));
        });
    }

}
