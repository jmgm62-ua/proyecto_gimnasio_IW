package iwebgym.dto;

import lombok.Data;
import java.util.List;
import java.util.Objects;

@Data
public class SocioData {

    private Long id;
    private String name;
    private String email;
    private String tipoCuota;
    private String fechaAlta;
    private String fechaBaja;
    private float saldo;
    private String inscripcion;
    private String direccion;
    private String fechaNacimiento;
    private String telefono;
    private Boolean activo;


    public SocioData() {
    }

    public SocioData(Long id, String name, String email, String tipoCuota, String fechaAlta, String fechaBaja, float saldo, String inscripcion, String direccion, String fechaNacimiento, String telefono, Boolean activo) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.tipoCuota = tipoCuota;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.saldo = saldo;
        this.inscripcion = inscripcion;
        this.direccion = direccion;
        this.fechaNacimiento =  fechaNacimiento;
        this.telefono = telefono;
        this.activo = activo;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SocioData)) return false;
        SocioData that = (SocioData) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
