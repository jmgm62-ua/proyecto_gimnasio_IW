package iwebgym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import iwebgym.model.TipoActividad;

import iwebgym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TipoActividadRepository extends JpaRepository<TipoActividad, Long> {}