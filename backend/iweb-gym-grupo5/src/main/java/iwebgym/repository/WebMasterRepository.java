package iwebgym.repository;

import iwebgym.model.Socio;
import iwebgym.model.WebMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WebMasterRepository extends JpaRepository<WebMaster, Long> {

    @Query("SELECT s FROM WebMaster s WHERE s.email = :email")
    Optional<WebMaster> findByEmail(@Param("email") String email);
    List<WebMaster> findAll();

}
