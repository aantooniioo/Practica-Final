package com.angeles.backend.controller;

import com.angeles.backend.entity.Proyecto;
import com.angeles.backend.repository.ProyectoRepository;
import com.angeles.backend.repository.EmpleadoProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private EmpleadoProyectoRepository empleadoProyectoRepository;

    @GetMapping
    public List<Proyecto> obtenerProyectos(){
        return proyectoRepository.findAll()
                .stream()
                .filter(p -> p.getFechaBaja() == null)
                .toList();
    }

    @PostMapping
    public Proyecto crearProyecto(@RequestBody Proyecto proyecto){
        return proyectoRepository.save(proyecto);
    }

    @PutMapping("/baja/{id}")
    public ResponseEntity<?> darDeBaja(@PathVariable Integer id){

        // Comprobamos si tiene empleados
        int asignaciones = empleadoProyectoRepository.countByIdProyecto(id);

        if(asignaciones > 0){
            return ResponseEntity
                    .badRequest()
                    .body("No se puede dar de baja el proyecto porque tiene empleados asignados");
        }

        Proyecto p = proyectoRepository.findById(id).orElse(null);

        if(p != null){
            p.setFechaBaja(LocalDate.now());
            proyectoRepository.save(p);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}