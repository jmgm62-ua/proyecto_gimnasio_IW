package iwebgym.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    @NonNull
    private String email;
    @NonNull
    private String password;
    private String fechaNacimiento;
    private String telefono;
    private String direccion;
    private Boolean activo;

    public User() {
    }

    public User(String name, String email, String password, String fechaNacimiento, String telefono, String direccion, Boolean activo) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.direccion = direccion;
        this.activo = activo;
    }


}
