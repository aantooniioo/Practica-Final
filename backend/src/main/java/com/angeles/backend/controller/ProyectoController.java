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

    // Obtiene todos los proyectos activos (sin fecha de baja)
    @GetMapping
    public List<Proyecto> obtenerProyectos(){
        return proyectoRepository.findByFechaBajaIsNull()
                .stream()
                .filter(p -> p.getFechaBaja() == null)
                .toList();
    }

    // Obtiene un proyecto por su id
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProyectoPorId(@PathVariable Integer id){

        Proyecto proyecto = proyectoRepository.findById(id).orElse(null);

        if(proyecto == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(proyecto);
    }

    // Crea un nuevo proyecto con validaciones básicas
    @PostMapping
    public ResponseEntity<?> crearProyecto(@RequestBody Proyecto proyecto){

        // Validar descripción obligatoria
        if(proyecto.getDescripcion() == null || proyecto.getDescripcion().isEmpty()){
            return ResponseEntity.badRequest().body("La descripción es obligatoria");
        }

        // Validar fecha de inicio
        if(proyecto.getFechaInicio() == null){
            return ResponseEntity.badRequest().body("La fecha de inicio es obligatoria");
        }

        // Validar lugar obligatorio
        if(proyecto.getLugar() == null || proyecto.getLugar().isEmpty()){
            return ResponseEntity.badRequest().body("El lugar es obligatorio");
        }

        return ResponseEntity.ok(proyectoRepository.save(proyecto));
    }

    // Actualiza los datos de un proyecto existente
    @PutMapping("/{id}")
    public ResponseEntity<?> editarProyecto(@PathVariable Integer id, @RequestBody Proyecto datos){

        Proyecto proyecto = proyectoRepository.findById(id).orElse(null);

        if(proyecto == null){
            return ResponseEntity.notFound().build();
        }

        // Validar descripción obligatoria
        if(datos.getDescripcion() == null || datos.getDescripcion().isEmpty()){
            return ResponseEntity.badRequest().body("La descripción es obligatoria");
        }

        // Validar fecha de inicio
        if(datos.getFechaInicio() == null){
            return ResponseEntity.badRequest().body("La fecha de inicio es obligatoria");
        }

        // Validar lugar obligatorio
        if(datos.getLugar() == null || datos.getLugar().isEmpty()){
            return ResponseEntity.badRequest().body("El lugar es obligatorio");
        }

        // Actualiza los campos del proyecto
        proyecto.setDescripcion(datos.getDescripcion());
        proyecto.setFechaInicio(datos.getFechaInicio());
        proyecto.setFechaFin(datos.getFechaFin());
        proyecto.setLugar(datos.getLugar());

        proyectoRepository.save(proyecto);

        return ResponseEntity.ok(proyecto);
    }

    // Da de baja un proyecto si no tiene empleados asignados
    @PutMapping("/baja/{id}")
    public ResponseEntity<?> darDeBaja(@PathVariable Integer id){

        // Comprueba si el proyecto tiene empleados asignados
        int asignaciones = empleadoProyectoRepository.countByIdProyecto(id);

        if(asignaciones > 0){
            return ResponseEntity
                    .badRequest()
                    .body("No se puede dar de baja el proyecto porque tiene empleados asignados");
        }

        Proyecto proyecto = proyectoRepository.findById(id).orElse(null);

        if(proyecto != null){
            proyecto.setFechaBaja(LocalDate.now());
            proyectoRepository.save(proyecto);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}