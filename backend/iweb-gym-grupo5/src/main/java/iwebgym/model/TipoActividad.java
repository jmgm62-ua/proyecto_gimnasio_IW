package iwebgym.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tipos_actividad")
public class TipoActividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private EnumNombreActividad nombre;

    private double precioExtra;

    @OneToMany(mappedBy = "tipoActividad")
    private Set<Actividad> actividades = new HashSet<>();

    public TipoActividad() {
    }

    public TipoActividad(EnumNombreActividad nombre, double precioExtra) {
        this.nombre = nombre;
        this.precioExtra = precioExtra;
    }

    public void addActividades(Actividad actividad) {
        this.actividades.add(actividad);
    }

}
