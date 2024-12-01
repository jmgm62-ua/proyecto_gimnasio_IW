package iwebgym.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "instalaciones")
public class Instalacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
}
