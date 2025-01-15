package iwebgym.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "reservas")
@Getter
@ToString(exclude = "socio")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "actividad_id")
    private Actividad actividad;

    @ManyToOne
    @JoinColumn(name = "socio_id")
    private Socio socio;

    public Reserva() {

    }



    public Reserva(Date fecha, Actividad actividad, Socio socio) {
        this.fecha = fecha;
        this.actividad = actividad;
        this.socio = socio;
    }

    public void addSocio(Socio socio) {
        this.socio = socio;
        socio.getReservas().add(this);
    }

    public void removeSocio(Socio socio) {
        this.socio = null;
        socio.getReservas().remove(this);
    }

    public void setId(Long id) {
        this.id = id;
    }
}
