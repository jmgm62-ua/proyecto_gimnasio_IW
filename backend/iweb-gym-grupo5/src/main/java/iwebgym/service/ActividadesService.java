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
                                actividad.getFechaFin()
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

}
