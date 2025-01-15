package iwebgym.repository;

import iwebgym.model.Suscripcion;
import iwebgym.model.TipoActividad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {
    boolean existsByTipoSuscripcion(String tipoSuscripcion);
}
