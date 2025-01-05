package iwebgym.dto;

import lombok.Data;
import java.util.List;
import java.util.Objects;

@Data
public class SubscriptionData {

    private String nombre;
    private Double precio;

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
