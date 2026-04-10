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

    // ✅ Obtener proyectos activos
    @GetMapping
    public List<Proyecto> obtenerProyectos(){
        return proyectoRepository.findAll()
                .stream()
                .filter(p -> p.getFechaBaja() == null)
                .toList();
    }

    // ✅ Obtener proyecto por ID (IMPORTANTE PARA EDITAR)
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProyectoPorId(@PathVariable Integer id){
        Proyecto proyecto = proyectoRepository.findById(id).orElse(null);

        if(proyecto == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(proyecto);
    }

    // ✅ Crear proyecto
    @PostMapping
    public ResponseEntity<?> crearProyecto(@RequestBody Proyecto proyecto){

        if(proyecto.getDescripcion() == null || proyecto.getDescripcion().isEmpty()){
            return ResponseEntity.badRequest().body("La descripción es obligatoria");
        }

        if(proyecto.getFechaInicio() == null){
            return ResponseEntity.badRequest().body("La fecha de inicio es obligatoria");
        }

        if(proyecto.getLugar() == null || proyecto.getLugar().isEmpty()){
            return ResponseEntity.badRequest().body("El lugar es obligatorio");
        }

        return ResponseEntity.ok(proyectoRepository.save(proyecto));
    }

    // ✅ EDITAR PROYECTO (LO QUE TE FALTABA)
    @PutMapping("/{id}")
    public ResponseEntity<?> editarProyecto(@PathVariable Integer id, @RequestBody Proyecto datos){

        Proyecto proyecto = proyectoRepository.findById(id).orElse(null);

        if(proyecto == null){
            return ResponseEntity.notFound().build();
        }

        // Validaciones
        if(datos.getDescripcion() == null || datos.getDescripcion().isEmpty()){
            return ResponseEntity.badRequest().body("La descripción es obligatoria");
        }

        if(datos.getFechaInicio() == null){
            return ResponseEntity.badRequest().body("La fecha de inicio es obligatoria");
        }

        if(datos.getLugar() == null || datos.getLugar().isEmpty()){
            return ResponseEntity.badRequest().body("El lugar es obligatorio");
        }

        // Actualizar campos
        proyecto.setDescripcion(datos.getDescripcion());
        proyecto.setFechaInicio(datos.getFechaInicio());
        proyecto.setFechaFin(datos.getFechaFin());
        proyecto.setLugar(datos.getLugar());

        proyectoRepository.save(proyecto);

        return ResponseEntity.ok(proyecto);
    }

    // ✅ Dar de baja
    @PutMapping("/baja/{id}")
    public ResponseEntity<?> darDeBaja(@PathVariable Integer id){

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