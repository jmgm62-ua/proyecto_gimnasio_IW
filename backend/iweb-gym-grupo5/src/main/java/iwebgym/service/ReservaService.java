package iwebgym.service;

import iwebgym.dto.ReservaData;
import iwebgym.dto.ReservaRequest;
import iwebgym.dto.SocioData;
import iwebgym.dto.StringIntTuple;
import iwebgym.model.*;
import iwebgym.repository.ActividadRepository;
import iwebgym.repository.ReservaRepository;
import iwebgym.repository.SocioRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservaService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private SocioRepository socioRepository;
    @Autowired
    private ActividadRepository actividadRepository;
    @Autowired
    private TipoActividadService tipoActividadService;
    @Autowired
    private IngresoService ingresoService;

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

    public Reserva crearReserva(ReservaRequest reservaRequest) throws Exception {
        Socio socio = socioRepository.findByEmail(reservaRequest.getEmail()).get();
        Double saldo_socio = Double.valueOf(socio.getSaldo());

        Actividad actividad = actividadRepository.findById(reservaRequest.getIdActividad()).get();
        StringIntTuple tuple = tipoActividadService.findNameAndPrice(actividad.getId());
        Double price = tuple.getPrice();

        if (saldo_socio < price) {
            throw new Exception("Error: El usuario no tiene saldo suficiente.");
        }

        saldo_socio = saldo_socio - price;
        socio.setSaldo(saldo_socio.floatValue());
        socioRepository.save(socio);

        //Comprobar cual es el ultimo id de reserva
        Long nextId = 1L; // Valor por defecto si no hay reservas
        List<Reserva> allReservas = reservaRepository.findAll();
        if (!allReservas.isEmpty()) {
            Long maxId = allReservas.stream()
                    .mapToLong(Reserva::getId)
                    .max()
                    .orElse(0L);
            nextId = maxId + 1;
        }


        String fechaString = reservaRequest.getFechaSeleccionada();
        String horaString = reservaRequest.getHoraInicio();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalDate fechaLocal = LocalDate.parse(fechaString, dateFormatter);
        LocalTime horaLocal = LocalTime.parse(horaString, timeFormatter);
        LocalDateTime fechaHoraSeleccionada = LocalDateTime.of(fechaLocal, horaLocal);


        Date fechaReserva = Date.from(fechaHoraSeleccionada.atZone(ZoneId.systemDefault()).toInstant());
        Reserva reserva = new Reserva(fechaReserva, actividad, socio);

        reserva.setId(nextId);
        return reservaRepository.save(reserva);
    }

    public List<SocioData> getAsistentes(Long actividadId, String date) {
        LocalDate activityDate = LocalDate.parse(date);
        java.util.Date startDate = java.util.Date.from(activityDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.util.Date endDate = java.util.Date.from(activityDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<Socio> socios = reservaRepository.findByActividadIdAndFechaBetween(actividadId, startDate, endDate)
                .stream()
                .map(Reserva::getSocio)
                .collect(Collectors.toList());

        List<SocioData> socioDataList = socios.stream()
                .map(SocioData::new)
                .collect(Collectors.toList());

        return socioDataList;
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