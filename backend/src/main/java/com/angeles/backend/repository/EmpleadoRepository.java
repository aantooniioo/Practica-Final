package com.angeles.backend.repository;

import com.angeles.backend.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    // Solo empleados activos
    List<Empleado> findByFechaBajaIsNull();

}