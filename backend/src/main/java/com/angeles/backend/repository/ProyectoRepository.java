package com.angeles.backend.repository;

import com.angeles.backend.entity.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Repositorio para la gestión de proyectos
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {

    // Devuelve únicamente los proyectos activos (sin fecha de baja)
    List<Proyecto> findByFechaBajaIsNull();

}