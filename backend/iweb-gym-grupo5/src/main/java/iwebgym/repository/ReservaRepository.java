package iwebgym.repository;

import iwebgym.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    // Cuenta el número de reservas para una actividad específica
    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.actividad.id = :actividadId")
    Long countByActividadId(@Param("actividadId") Long actividadId);

    // Encuentra todas las reservas para una actividad específica
    List<Reserva> findByActividadId(Long actividadId);

    // Buscar reservas históricas usando email
    @Query("SELECT r FROM Reserva r WHERE r.socio.id IN (SELECT s.id FROM Socio s WHERE s.email = :email) AND r.fecha < CURRENT_TIMESTAMP ORDER BY r.fecha DESC")
    List<Reserva> findHistoricalByEmail(@Param("email") String email);

    // Buscar reservas pendientes usando email
    @Query("SELECT r FROM Reserva r WHERE r.socio.id IN (SELECT s.id FROM Socio s WHERE s.email = :email) AND r.fecha >= CURRENT_TIMESTAMP ORDER BY r.fecha ASC")
    List<Reserva> findPendingByEmail(@Param("email") String email);

    @Query("SELECT COUNT(DISTINCT r.socio.id) FROM Reserva r " +
            "WHERE r.fecha BETWEEN :fechaInicio AND :fechaFin")
    Long countDistinctSociosByFechaBetween(
            @Param("fechaInicio") Date fechaInicio,
            @Param("fechaFin") Date fechaFin
    );

    List<Reserva> findByFechaBetween(Date fechaInicio, Date fechaFin);
}
