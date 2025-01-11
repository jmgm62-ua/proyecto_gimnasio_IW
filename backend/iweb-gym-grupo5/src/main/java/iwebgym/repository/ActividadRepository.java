package iwebgym.repository;

import iwebgym.model.Actividad;
import iwebgym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ActividadRepository extends JpaRepository<User, Long> {
    // Busca actividades por monitor donde la fecha fin es anterior a la fecha dada
    @Query("SELECT a FROM Actividad a WHERE a.monitor.email = :email AND a.fechaFin < :fecha")
    List<Actividad> findByMonitorAndFechaFinBefore(
            @Param("email") String email,
            @Param("fecha") LocalDateTime fecha
    );

    // Busca actividades por monitor donde la fecha fin es posterior a la fecha dada
    @Query("SELECT a FROM Actividad a WHERE a.monitor.email = :email AND a.fechaFin >= :fecha")
    List<Actividad> findByMonitorAndFechaFinAfter(
            @Param("email") String email,
            @Param("fecha") LocalDateTime fecha
    );
}
