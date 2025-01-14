package iwebgym.repository;

import iwebgym.model.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface IngresoRepository extends JpaRepository<Ingreso, Long> {
    // Método para buscar ingresos entre fechas
    List<Ingreso> findByFechaBetween(Date fechaInicio, Date fechaFin);

    // Método para calcular la suma de ingresos entre fechas
    @Query("SELECT SUM(i.cantidad) FROM Ingreso i WHERE i.fecha BETWEEN :fechaInicio AND :fechaFin")
    BigDecimal sumCantidadBetweenFechas(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);
}