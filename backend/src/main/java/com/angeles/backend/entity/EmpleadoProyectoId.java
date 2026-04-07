package com.angeles.backend.entity;

import java.io.Serializable;
import java.util.Objects;

public class EmpleadoProyectoId implements Serializable {

    private Integer idProyecto;
    private Integer idEmpleado;

    public EmpleadoProyectoId() {}

    public EmpleadoProyectoId(Integer idProyecto, Integer idEmpleado) {
        this.idProyecto = idProyecto;
        this.idEmpleado = idEmpleado;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmpleadoProyectoId)) return false;
        EmpleadoProyectoId that = (EmpleadoProyectoId) o;
        return Objects.equals(idProyecto, that.idProyecto) &&
                Objects.equals(idEmpleado, that.idEmpleado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProyecto, idEmpleado);
    }
}