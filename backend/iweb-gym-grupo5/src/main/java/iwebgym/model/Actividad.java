package iwebgym.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "actividades")
public class Actividad {

    @Id
    private Long id;

    private String nombre;
    private String diaSemana;

    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    private Date fechaFin;

    private String horaInicio;
    private String horaFin;

    @ManyToMany(mappedBy = "actividades")
    Set<WebMaster> webMasters = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_actividad_id")
    private TipoActividad tipoActividad;

    @Getter
    @OneToMany(mappedBy = "actividad")
    private List<Reserva> reservas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monitor_id")
    private Monitor monitor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instalacion_id")
    private Instalacion instalacion;

    public Actividad() {
    }

    public Actividad(String nombre, String diaSemana, String horaInicio, String horaFin, Date fechaInicio, Date fechaFin, TipoActividad tipoActividad) {
        this.nombre = nombre;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tipoActividad = tipoActividad;
    }


    // Helper methods para manejar las relaciones
    public void addWebMaster(WebMaster webMaster) {
        this.webMasters.add(webMaster);
        webMaster.getActividades().add(this);
    }

    public void removeWebMaster(WebMaster webMaster) {
        this.webMasters.remove(webMaster);
        webMaster.getActividades().remove(this);
    }
}
