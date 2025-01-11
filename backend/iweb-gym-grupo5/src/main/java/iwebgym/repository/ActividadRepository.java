package iwebgym.repository;

import iwebgym.model.Actividad;
import iwebgym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActividadRepository extends JpaRepository<Actividad, Long> {
    List<Actividad> findByMonitorId(Long monitorId);
}
