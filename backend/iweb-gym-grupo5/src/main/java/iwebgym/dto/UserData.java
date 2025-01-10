package iwebgym.dto;

import java.util.Date;
import java.util.Objects;

public class UserData {

    private Long id;
    private String email;
    private String nombre;
    private Date fechaNacimiento;
    private boolean admin;
    private boolean block;

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean getAdmin(){return admin;}

    public boolean getBlock(){return block;}

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setAdmin(boolean admin){this.admin = admin;}

    public void setBlock(boolean block){this.block = block;}
    // Sobreescribimos equals y hashCode para que dos usuarios sean iguales
    // si tienen el mismo ID (ignoramos el resto de atributos)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserData)) return false;
        UserData that = (UserData) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
