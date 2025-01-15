package iwebgym.repository;

import iwebgym.model.Socio;
import iwebgym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SocioRepository extends JpaRepository<Socio, Long> {

    @Query("SELECT s FROM Socio s WHERE s.email = :email")
    Optional<Socio> findByEmail(@Param("email") String email);

    @Query("SELECT s FROM Socio s WHERE s.activo = true AND s.fechaAlta <= :fecha AND (s.fechaBaja IS NULL OR s.fechaBaja > :fecha)")
    List<Socio> findActiveSociosAtDate(@Param("fecha") String fecha);

    @Query("SELECT COUNT(s) FROM Socio s WHERE s.activo = true AND s.fechaAlta <= :fecha AND (s.fechaBaja IS NULL OR s.fechaBaja > :fecha)")
    Long countActiveSociosAtDate(@Param("fecha") String fecha);

    @Query("SELECT s FROM Socio s WHERE s.fechaAlta BETWEEN :fechaInicio AND :fechaFin")
    List<Socio> findNewSociosBetweenDates(@Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin);

    @Query("SELECT COUNT(s) FROM Socio s WHERE s.fechaAlta BETWEEN :fechaInicio AND :fechaFin")
    Long countNewSociosBetweenDates(@Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin);

    List<Socio> findAll();

    @Query("SELECT s FROM Socio s WHERE s.activo = false AND s.fechaAlta IS NULL")
    List<Socio> findInactiveSociosWithoutFechaAlta();

    @Query("SELECT s FROM Socio s WHERE s.fechaAlta IS NOT NULL")
    List<Socio> findAllbutNotSolicitudes();

}
