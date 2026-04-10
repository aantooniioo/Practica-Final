package com.angeles.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

// Representa la asignación de un empleado a un proyecto
@Entity
@Table(name = "PR_EMPLEADOS_PROYECTO")
@IdClass(EmpleadoProyectoId.class)
public class EmpleadoProyecto {

    // Clave compuesta: idProyecto + idEmpleado

    @Id
    @Column(name = "ID_PROYECTO")
    private Integer idProyecto;

    @Id
    @Column(name = "ID_EMPLEADO")
    private Integer idEmpleado;

    // Fecha en la que el empleado fue asignado al proyecto
    @Column(name = "F_ALTA")
    private LocalDate fechaAlta;

    // Constructor vacío requerido por JPA
    public EmpleadoProyecto() {}

    // Getters y setters

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