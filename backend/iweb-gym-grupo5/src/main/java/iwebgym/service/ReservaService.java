package iwebgym.service;

import iwebgym.dto.ReservaData;
import iwebgym.model.Reserva;
import iwebgym.repository.ReservaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ReservaRepository reservaRepository;

    public List<ReservaData> getReservasHistoricas(String email) {
        return reservaRepository.findHistoricalByEmail(email)
                .stream()
                .map(this::mapReservaToReservaData)
                .collect(Collectors.toList());
    }

    public List<ReservaData> getReservasPendientes(String email) {
        return reservaRepository.findPendingByEmail(email)
                .stream()
                .map(this::mapReservaToReservaData)
                .collect(Collectors.toList());
    }

    private ReservaData mapReservaToReservaData(Reserva reserva) {
        ReservaData reservaData = new ReservaData();
        reservaData.setId(reserva.getId());
        reservaData.setFecha(reserva.getFecha());
        reservaData.setActividad(reserva.getActividad());
        reservaData.setSocio(reserva.getSocio());
        reservaData.setNombreActividad(reserva.getActividad().getNombre());
        return reservaData;
    }
}