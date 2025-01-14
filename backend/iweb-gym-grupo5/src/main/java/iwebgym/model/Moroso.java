package iwebgym.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "morosos")
public class Moroso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long id_socio;

    private String mensualidad_no_pagada;

    // Constructores
    public Moroso() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_socio() {
        return id_socio;
    }

    public void setId_socio(Long id_socio) {
        this.id_socio = id_socio;
    }

    public String getMensualidad_no_pagada() {
        return mensualidad_no_pagada;
    }

    public void setMensualidad_no_pagada(String mensualidad_no_pagada) {
        this.mensualidad_no_pagada = mensualidad_no_pagada;
    }
}