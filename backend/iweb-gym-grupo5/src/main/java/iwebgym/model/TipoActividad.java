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

    private String nombre;

    private double precioExtra;

    @OneToMany(mappedBy = "tipoActividad")
    private Set<Actividad> actividades = new HashSet<>();

    public TipoActividad() {
    }

    public TipoActividad(String nombre, double precioExtra) {
        this.nombre = nombre;
        this.precioExtra = precioExtra;
    }

    public void addActividades(Actividad actividad) {
        this.actividades.add(actividad);
    }

    public void removeActividades(Actividad actividad) {
        this.actividades.remove(actividad);
    }

    public String ObtenerNombreById(int id) {
        switch (id) {
            case 0:
                return "Spinning";

            case 1:
                return "Crossfit";

            case 2:
                return "Pilates";

            case 3:
                return "HIIT";
            default:
                return "No existe";
        }
    }

}
