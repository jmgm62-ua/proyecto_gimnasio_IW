package iwebgym.repository;

import iwebgym.model.Actividad;
import iwebgym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActividadRepository extends JpaRepository<Actividad, Long> {
}
