package iwebgym.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.List;

import java.util.List;

@Entity
@Data
@Table(name = "socios")
public class Socio extends User {

    private String tipoCuota;
    private String fechaAlta;
    private String fechaBaja;
    private float Saldo;

    @ManyToOne
    @JoinColumn(name = "inscripcion_id")
    private Suscripcion inscripcion;

    @OneToMany(mappedBy = "socio", cascade = CascadeType.ALL)
    private List<Reserva_instalacion> reservaInstalacions;

    @Getter
    @OneToMany(mappedBy = "socio")
    private List<Reserva> reservas;

    public Socio() {
    }

    // Constructor con parámetros
    public Socio(String name, String email, String password, String fechaNacimiento, String telefono, String direccion, Boolean activo, String tipoCuota, String fechaAlta, String fechaBaja, float Saldo) {
        super(name, email, password, fechaNacimiento, telefono, direccion, activo);
        this.tipoCuota = tipoCuota;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.Saldo = Saldo;
    }
}
