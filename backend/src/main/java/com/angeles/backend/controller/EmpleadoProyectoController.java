package com.angeles.backend.controller;

import com.angeles.backend.entity.EmpleadoProyecto;
import com.angeles.backend.repository.EmpleadoProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asignaciones")
@CrossOrigin(origins = "http://localhost:5173")
public class EmpleadoProyectoController {

    @Autowired
    private EmpleadoProyectoRepository empleadoProyectoRepository;

    // Obtiene todas las asignaciones existentes
    @GetMapping
    public List<EmpleadoProyecto> listar(){
        return empleadoProyectoRepository.findAll();
    }

    // Asigna un empleado a un proyecto con validación de duplicados
    @PostMapping
    public ResponseEntity<?> asignar(@RequestBody EmpleadoProyecto ep){

        // Comprueba si ya existe la asignación
        boolean existe = empleadoProyectoRepository.existsByIdEmpleadoAndIdProyecto(
                ep.getIdEmpleado(),
                ep.getIdProyecto()
        );

        if(existe){
            return ResponseEntity
                    .badRequest()
                    .body("El empleado ya está asignado a este proyecto");
        }

        return ResponseEntity.ok(empleadoProyectoRepository.save(ep));
    }
}