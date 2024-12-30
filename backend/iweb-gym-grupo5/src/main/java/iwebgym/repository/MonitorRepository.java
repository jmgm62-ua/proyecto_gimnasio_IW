package iwebgym.repository;

import iwebgym.model.Monitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MonitorRepository extends JpaRepository<Monitor, Long> {

    @Query("SELECT s FROM Monitor s WHERE s.email = :email")
    Optional<Monitor> findByEmail(@Param("email") String email);
    List<Monitor> findAll();

}
