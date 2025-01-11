package iwebgym.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ActividadData {

    private Long id;
    private String nombre;
    private Date fechaInicio;
    private Date fechaFin;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private Long numAsistentes;
    // Calendar format specific fields
    private Date start;
    private Date end;
}
