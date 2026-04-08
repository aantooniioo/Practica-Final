package com.angeles.backend.repository;

import com.angeles.backend.entity.EmpleadoProyecto;
import com.angeles.backend.entity.EmpleadoProyectoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoProyectoRepository
        extends JpaRepository<EmpleadoProyecto, EmpleadoProyectoId> {

    boolean existsByIdEmpleadoAndIdProyecto(Integer idEmpleado, Integer idProyecto);

    // Contar asignaciones por empleado
    int countByIdEmpleado(Integer idEmpleado);

    // Contar asignaciones por proyecto
    int countByIdProyecto(Integer idProyecto);
}