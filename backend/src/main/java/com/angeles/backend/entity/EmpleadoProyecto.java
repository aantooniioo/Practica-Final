package com.angeles.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PR_EMPLEADOS_PROYECTO")
@IdClass(EmpleadoProyectoId.class)
public class EmpleadoProyecto {

    @Id
    @Column(name = "ID_PROYECTO")
    private Integer idProyecto;

    @Id
    @Column(name = "ID_EMPLEADO")
    private Integer idEmpleado;

    @Column(name = "F_ALTA")
    private LocalDate fechaAlta;

    public EmpleadoProyecto() {}

    public Integer getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
}