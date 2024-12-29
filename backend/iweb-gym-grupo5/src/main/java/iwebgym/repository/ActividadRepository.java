package iwebgym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import iwebgym.model.Actividad;

@Repository
public interface ActividadRepository extends JpaRepository<User, Long> {
}
