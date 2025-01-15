package iwebgym.dto;

import java.util.Date;

public class MorosoDTO {
    private Long id;
    private Long socioId;
    private String name;
    private String email;
    private String direccion;
    private String telefono;
    private String fechaNacimiento;
    private String mensualidadNoPagada;
    private boolean activo;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getSocioId() { return socioId; }
    public void setSocioId(Long socioId) { this.socioId = socioId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getMensualidadNoPagada() { return mensualidadNoPagada; }
    public void setMensualidadNoPagada(String mensualidadNoPagada) { this.mensualidadNoPagada = mensualidadNoPagada; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}