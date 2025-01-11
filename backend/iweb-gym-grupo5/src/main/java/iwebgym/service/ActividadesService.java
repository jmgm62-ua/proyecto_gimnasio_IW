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

@Service
public class ActividadesService {

    @Autowired
    private ActividadRepository actividadRepository;
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



}
