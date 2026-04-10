package com.angeles.backend.entity;

import java.io.Serializable;
import java.util.Objects;

// Clase que representa la clave compuesta de EmpleadoProyecto
public class EmpleadoProyectoId implements Serializable {

    // Identificador del proyecto
    private Integer idProyecto;

    // Identificador del empleado
    private Integer idEmpleado;

    // Constructor vacío requerido
    public EmpleadoProyectoId() {}

    // Constructor con parámetros
    public EmpleadoProyectoId(Integer idProyecto, Integer idEmpleado) {
        this.idProyecto = idProyecto;
        this.idEmpleado = idEmpleado;
    }

    // Equals necesario para comparar claves compuestas correctamente
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmpleadoProyectoId that = (EmpleadoProyectoId) o;
        return Objects.equals(idProyecto, that.idProyecto) &&
                Objects.equals(idEmpleado, that.idEmpleado);
    }

    // HashCode necesario para el funcionamiento interno de JPA
    @Override
    public int hashCode() {
        return Objects.hash(idProyecto, idEmpleado);
    }
}