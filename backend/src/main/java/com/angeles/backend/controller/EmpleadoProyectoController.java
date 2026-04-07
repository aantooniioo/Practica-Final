package com.angeles.backend.controller;

import com.angeles.backend.entity.EmpleadoProyecto;
import com.angeles.backend.repository.EmpleadoProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asignaciones")
@CrossOrigin(origins = "http://localhost:5173")
public class EmpleadoProyectoController {

    @Autowired
    private EmpleadoProyectoRepository repo;

    // GET -> Ver todas las asignaciones
    @GetMapping
    public List<EmpleadoProyecto> listar() {
        return repo.findAll();
    }

    // POST -> Asignar empleado a proyecto
    @PostMapping
    public EmpleadoProyecto asignar(@RequestBody EmpleadoProyecto ep){
        return repo.save(ep);
    }
}