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

    public double getPrecioExtra() {
        return precioExtra;
    }

    public void setPrecioExtra(double precioExtra) {
        this.precioExtra = precioExtra;
    }

    private double precioExtra;

    @OneToMany(mappedBy = "tipoActividad")
    private Set<Actividad> actividades = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

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
