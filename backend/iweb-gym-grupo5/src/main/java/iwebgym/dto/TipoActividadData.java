package iwebgym.dto;

import iwebgym.model.TipoActividad;
import iwebgym.model.WebMaster;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TipoActividadData {

    private Long id;
    private String nombre;
    private double precioExtra;

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

    public double getPrecioExtra() {
        return precioExtra;
    }

    public void setPrecioExtra(double precioExtra) {
        this.precioExtra = precioExtra;
    }
}
