package iwebgym.repository;

import iwebgym.model.Socio;
import iwebgym.model.Suscripcion;
import iwebgym.model.TipoActividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {
    boolean existsByTipoSuscripcion(String tipoSuscripcion);

    @Query("SELECT s.id FROM Suscripcion s WHERE s.tipoSuscripcion = :nombreSuscripcion")
    Long findIdByNombreSuscripcion(@Param("nombreSuscripcion") String nombreSuscripcion);
}
