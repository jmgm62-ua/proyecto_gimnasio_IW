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

    List<Socio> findAll();

    List<Socio> findByActivoTrue();
}
