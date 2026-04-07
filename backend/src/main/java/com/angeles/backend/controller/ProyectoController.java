package com.angeles.backend.controller;

import com.angeles.backend.entity.Proyecto;
import com.angeles.backend.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @GetMapping
    public List<Proyecto> obtenerProyectos(){
        return proyectoRepository.findAll()
                .stream()
                .filter(p -> p.getFechaBaja() == null)
                .toList();
    }
}