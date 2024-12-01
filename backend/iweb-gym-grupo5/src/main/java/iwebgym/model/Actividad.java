package iwebgym.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "actividades")
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private Date fechaInicio;
    private Date fechaFin;
}
