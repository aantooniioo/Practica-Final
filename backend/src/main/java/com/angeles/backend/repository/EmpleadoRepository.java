package com.angeles.backend.repository;

import com.angeles.backend.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Repositorio para la gestión de empleados en base de datos
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    // Comprueba si ya existe un email en la base de datos
    boolean existsByEmail(String email);

    // Devuelve únicamente los empleados activos (sin fecha de baja)
    List<Empleado> findByFechaBajaIsNull();

}