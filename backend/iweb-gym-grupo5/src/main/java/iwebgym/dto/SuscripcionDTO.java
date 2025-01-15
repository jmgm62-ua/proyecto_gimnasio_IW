package iwebgym.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class SuscripcionDTO {
    private Long id;

    @NotBlank(message = "El tipo de suscripción es obligatorio")
    private String tipoSuscripcion;

    @Positive(message = "El precio debe ser mayor que 0")
    private float price;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTipoSuscripcion() { return tipoSuscripcion; }
    public void setTipoSuscripcion(String tipoSuscripcion) { this.tipoSuscripcion = tipoSuscripcion; }
    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
}
