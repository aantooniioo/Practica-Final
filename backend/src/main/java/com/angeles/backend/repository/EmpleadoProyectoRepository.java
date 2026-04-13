package com.angeles.backend.repository;

import com.angeles.backend.entity.EmpleadoProyecto;
import com.angeles.backend.entity.EmpleadoProyectoId;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositorio para la gestión de asignaciones entre empleados y proyectos
public interface EmpleadoProyectoRepository
        extends JpaRepository<EmpleadoProyecto, EmpleadoProyectoId> {

    // Comprueba si un empleado ya está asignado a un proyecto
    boolean existsByIdEmpleadoAndIdProyecto(Integer idEmpleado, Integer idProyecto);

    // Devuelve el número de asignaciones de un empleado
    int countByIdEmpleado(Integer idEmpleado);

    // Devuelve el número de empleados asignados a un proyecto
    int countByIdProyecto(Integer idProyecto);

    /**
     * Elimina una asignación concreta mediante sus identificadores
     */
    void deleteByIdEmpleadoAndIdProyecto(Integer idEmpleado, Integer idProyecto);
}