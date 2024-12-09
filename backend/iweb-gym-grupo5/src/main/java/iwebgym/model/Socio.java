package iwebgym.model;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Entity
@Table(name = "socios")
public class Socio extends User {
    private String tipoCuota;
    private String fechaAlta;
    private String fechaBaja;
    private float Saldo;

    @Getter
    @OneToMany(mappedBy = "socio")
    private List<Reserva> reservas;

    public Socio() {
    }

    public Socio(String name, String email, String password, String fechaNacimiento, String telefono, String direccion, Boolean activo, String tipoCuota, String fechaAlta, String fechaBaja, float Saldo) {
        super(name, email, password, fechaNacimiento, telefono, direccion, activo);
        this.tipoCuota = tipoCuota;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.Saldo = Saldo;
    }
}
