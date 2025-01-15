package iwebgym.dto;

import iwebgym.model.TipoActividad;
import iwebgym.model.WebMaster;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ActividadData {

    private Long id;
    private String nombre;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private Date fechaInicio;
    private Date fechaFin;
    public String tipo_de_actividad;
    public double precio_extra_actividad;
    private Long instalacionId;
    private Long monitorId;

    private Long tipoActividadId;

    public ActividadData(Long id, String nombre, String diaSemana, String horaInicio, String horaFin, Date fechaInicio, Date fechaFin, String tipo_de_actividad, double precio_extra_actividad) {
        this.id = id;
        this.nombre = nombre;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tipo_de_actividad = tipo_de_actividad;
        this.precio_extra_actividad = precio_extra_actividad;
    }

    public ActividadData() {
    }

    public ActividadData(Long id, String nombre, String diaSemana, String horaInicio, String horaFin, Date fechaInicio, Date fechaFin, double precio_extra_actividad, Long instalacionId, Long monitorId, Long tipoActividadId) {
        this.id = id;
        this.nombre = nombre;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precio_extra_actividad = precio_extra_actividad;
        this.instalacionId = instalacionId;
        this.monitorId = monitorId;
        this.tipoActividadId = tipoActividadId;
    }

    public Long getId() {
        return id;
    }

    public Long getInstalacionId() {
        return instalacionId;
    }


    public Long getTipoActividadId() {
        return tipoActividadId;
    }

    public void setTipoActividadId(Long tipoActividadId) {
        this.tipoActividadId = tipoActividadId;
    }

    public void setInstalacionId(Long instalacionId) {
        this.instalacionId = instalacionId;
    }

    public Long getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(Long monitorId) {
        this.monitorId = monitorId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getTipo_de_actividad() {
        return tipo_de_actividad;
    }

    public void setTipo_de_actividad(String tipo_de_actividad) {
        this.tipo_de_actividad = tipo_de_actividad;
    }

    public double getPrecio_extra_actividad() {
        return precio_extra_actividad;
    }

    public void setPrecio_extra_actividad(double precio_extra_actividad) {
        this.precio_extra_actividad = precio_extra_actividad;
    }

}
