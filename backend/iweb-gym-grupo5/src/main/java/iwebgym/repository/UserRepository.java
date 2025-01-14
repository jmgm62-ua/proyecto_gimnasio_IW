package iwebgym.repository;

import iwebgym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String s);
    List<User> findAll();

    @Query("SELECT u FROM Usuario u WHERE u.activo = false")
    List<Usuario> findInactiveUsers();

    List<Usuario> findByFechaAltaIsNullAndActivoFalse();
}
