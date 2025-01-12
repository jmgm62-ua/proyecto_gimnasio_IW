package iwebgym.service;

import iwebgym.dto.ActividadData;
import iwebgym.dto.StringIntTuple;
import iwebgym.model.Actividad;
import iwebgym.model.TipoActividad;
import iwebgym.repository.ActividadRepository;
import iwebgym.repository.TipoActividadRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TipoActividadService {

    @Autowired
    private TipoActividadRepository tipoActividadRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ActividadRepository actividadRepository;

    @Transactional(readOnly = true)
    public ArrayList<String> findAllTipos() {
        List<TipoActividad> listaTiposActividad = tipoActividadRepository.findAll();
        ArrayList<String> tipos = new ArrayList<>();
        for (TipoActividad tipoActividad : listaTiposActividad) {
            int id_seleccionada = Integer.parseInt(tipoActividad.getNombre());
            tipos.add(tipoActividad.ObtenerNombreById(id_seleccionada));
        }
        return tipos;
    }


    @Transactional(readOnly = true)
    public StringIntTuple findNameAndPrice(Long idActividad) {
        Optional<Actividad> optionalActividad = actividadRepository.findById(idActividad);

        Actividad actividad = optionalActividad.get();
        TipoActividad tipoActividad = actividad.getTipoActividad();
        String nombre_tipo = tipoActividad.ObtenerNombreById(Integer.parseInt(tipoActividad.getNombre()));
        double precio = tipoActividad.getPrecioExtra();

        return new StringIntTuple(nombre_tipo, precio);
    }




}
