package iwebgym.repository;

import iwebgym.model.Ingreso;
import iwebgym.model.IngresoPendiente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface IngresoPendienteRepository extends JpaRepository<IngresoPendiente, Long> {
}