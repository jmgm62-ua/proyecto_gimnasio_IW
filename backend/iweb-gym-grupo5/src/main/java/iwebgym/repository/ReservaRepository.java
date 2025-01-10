package iwebgym.repository;

import iwebgym.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    // Buscar reservas históricas usando email
    @Query("SELECT r FROM Reserva r WHERE r.socio.id IN (SELECT s.id FROM Socio s WHERE s.email = :email) AND r.fecha < CURRENT_TIMESTAMP ORDER BY r.fecha DESC")
    List<Reserva> findHistoricalByEmail(@Param("email") String email);

    // Buscar reservas pendientes usando email
    @Query("SELECT r FROM Reserva r WHERE r.socio.id IN (SELECT s.id FROM Socio s WHERE s.email = :email) AND r.fecha >= CURRENT_TIMESTAMP ORDER BY r.fecha ASC")
    List<Reserva> findPendingByEmail(@Param("email") String email);
}
