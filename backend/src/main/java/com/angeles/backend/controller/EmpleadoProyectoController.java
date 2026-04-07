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
    private EmpleadoProyectoRepository repo;

    // 🔹 GET → listar asignaciones
    @GetMapping
    public List<EmpleadoProyecto> listar(){
        return repo.findAll();
    }

    // 🔹 POST → asignar empleado a proyecto (con validación)
    @PostMapping
    public ResponseEntity<?> asignar(@RequestBody EmpleadoProyecto ep){

        boolean existe = repo.existsByIdEmpleadoAndIdProyecto(
                ep.getIdEmpleado(),
                ep.getIdProyecto()
        );

        if(existe){
            return ResponseEntity
                    .badRequest()
                    .body("El empleado ya está asignado a este proyecto");
        }

        return ResponseEntity.ok(repo.save(ep));
    }
}