package iwebgym.service;

import iwebgym.model.Ingreso;
import iwebgym.repository.IngresoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class IngresoService {
    @Autowired
    private IngresoRepository ingresoRepository;

    public Ingreso saveIngreso(Ingreso ingreso) {
        return ingresoRepository.save(ingreso);
    }

    public List<Ingreso> getIngresosBetweenFechas(Date fechaInicio, Date fechaFin) {
        return ingresoRepository.findByFechaBetween(fechaInicio, fechaFin);
    }

    public BigDecimal getTotalIngresosBetweenFechas(Date fechaInicio, Date fechaFin) {
        BigDecimal total = ingresoRepository.sumCantidadBetweenFechas(fechaInicio, fechaFin);
        return total != null ? total : BigDecimal.ZERO;
    }
}