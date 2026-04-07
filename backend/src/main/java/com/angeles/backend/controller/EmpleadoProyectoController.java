package com.angeles.backend.controller;

import com.angeles.backend.entity.EmpleadoProyecto;
import com.angeles.backend.repository.EmpleadoProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/asignaciones")
@CrossOrigin(origins = "http://localhost:5173")
public class EmpleadoProyectoController {

    @Autowired
    private EmpleadoProyectoRepository repo;

    @PostMapping
    public EmpleadoProyecto asignar(@RequestBody EmpleadoProyecto ep){
        return repo.save(ep);
    }
}