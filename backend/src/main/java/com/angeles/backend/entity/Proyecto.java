package com.angeles.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PR_PROYECTOS")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PROYECTO")
    private Integer idProyecto;

    @Column(name = "TX_DESCRIPCIÓN", nullable = false)
    private String descripcion;

    @Column(name = "F_INICIO", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "F_FIN")
    private LocalDate fechaFin;

    @Column(name = "TX_LUGAR")
    private String lugar;

    @Column(name = "F_BAJA")
    private LocalDate fechaBaja;

    public Proyecto(){}

    // getters y setters
    public Integer getIdProyecto() { return idProyecto; }
    public void setIdProyecto(Integer idProyecto) { this.idProyecto = idProyecto; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }

    public LocalDate getFechaBaja() { return fechaBaja; }
    public void setFechaBaja(LocalDate fechaBaja) { this.fechaBaja = fechaBaja; }
}