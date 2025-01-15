package iwebgym.repository;

import iwebgym.model.Actividad;
import iwebgym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActividadRepository extends JpaRepository<Actividad, Long> {
    List<Actividad> findByMonitorId(Long monitorId);

    @Query("SELECT a FROM Actividad a WHERE a.instalacion.id = :instalacionId AND a.diaSemana = :diaSemana")
    List<Actividad> findByInstalacionIdAndDiaSemana(
            @Param("instalacionId") Long instalacionId,
            @Param("diaSemana") String diaSemana
    );
}
