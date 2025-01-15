package iwebgym.repository;

import iwebgym.model.Actividad;
import iwebgym.model.Instalacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstalacionRepository extends JpaRepository<Instalacion, Long> {
}
