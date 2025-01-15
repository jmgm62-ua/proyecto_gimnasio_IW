package iwebgym.repository;

import iwebgym.model.Moroso;
import iwebgym.model.Suscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MorosoRepository extends JpaRepository<Moroso, Long> {

        @Query("SELECT m.id FROM Moroso m WHERE m.id_socio = :socioId")
        Long obtainIdFromIdSocio(@Param("socioId") Long socioId);

}
