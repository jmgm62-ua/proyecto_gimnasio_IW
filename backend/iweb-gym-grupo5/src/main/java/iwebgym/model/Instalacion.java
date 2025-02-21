package iwebgym.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "instalaciones")
public class Instalacion {
    @Id
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "instalacion", cascade = CascadeType.ALL)
    private List<Reserva_instalacion> reservaInstalacions;

}
