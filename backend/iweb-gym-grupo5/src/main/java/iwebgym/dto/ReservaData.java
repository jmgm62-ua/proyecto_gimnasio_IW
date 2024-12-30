package iwebgym.dto;

import iwebgym.model.Actividad;
import iwebgym.model.Socio;
import lombok.Data;

import java.util.Date;

@Data
public class ReservaData {

    private Long id;
    private Date fecha;
    private String nombreActividad;  // Nombre de la actividad, que puede ser una propiedad de la entidad `Actividad`
    private Long actividad_id;       // ID de la actividad
    private Long socio_id;           // ID del socio

    // Constructor vacío necesario para ModelMapper
    public ReservaData() {}

    // Constructor con todos los campos
    public ReservaData(Long id, Date fecha, String nombreActividad, Long actividad_id, Long socio_id) {
        this.id = id;
        this.fecha = fecha;
        this.nombreActividad = nombreActividad;
        this.actividad_id = actividad_id;
        this.socio_id = socio_id;
    }

    // Métodos adicionales para llenar la información relacionada con `Actividad`
    public void setActividad(Actividad actividad) {
        if (actividad != null) {
            this.actividad_id = actividad.getId();  // Asignar solo el ID
            this.nombreActividad = actividad.getNombre();  // Asignar el nombre de la actividad (si es necesario)
        }
    }

    // Métodos adicionales para llenar la información relacionada con `Socio`
    public void setSocio(Socio socio) {
        if (socio != null) {
            this.socio_id = socio.getId();  // Asignar solo el ID
        }
    }
}
