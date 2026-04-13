package com.angeles.backend.controller;

import com.angeles.backend.entity.EmpleadoProyecto;
import com.angeles.backend.entity.EmpleadoProyectoId; // IMPORT AÑADIDO
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

    /**
     * Elimina una asignación existente entre empleado y proyecto
     */
    @DeleteMapping("/{idEmpleado}/{idProyecto}")
    public ResponseEntity<?> eliminarAsignacion(
            @PathVariable Integer idEmpleado,
            @PathVariable Integer idProyecto
    ){

        try {

            // Construye la clave compuesta de la asignación
            EmpleadoProyectoId id = new EmpleadoProyectoId(idProyecto, idEmpleado);

            // Elimina la asignación usando la clave completa
            empleadoProyectoRepository.deleteById(id);

            return ResponseEntity.ok().build();

        } catch (Exception e){

            return ResponseEntity
                    .status(500)
                    .body("Error al eliminar la asignación: " + e.getMessage());
        }
    }

    /**
     * Actualiza la fecha de una asignación existente
     */
    @PutMapping("/{idEmpleado}/{idProyecto}")
    public ResponseEntity<?> editarAsignacion(
            @PathVariable Integer idEmpleado,
            @PathVariable Integer idProyecto,
            @RequestBody EmpleadoProyecto datos
    ){

        try {

            // Construye la clave compuesta
            EmpleadoProyectoId id = new EmpleadoProyectoId(idProyecto, idEmpleado);

            // Busca la asignación existente
            EmpleadoProyecto existente = empleadoProyectoRepository.findById(id).orElse(null);

            if(existente == null){
                return ResponseEntity.notFound().build();
            }

            // Actualiza la fecha
            existente.setFechaAlta(datos.getFechaAlta());

            empleadoProyectoRepository.save(existente);

            return ResponseEntity.ok(existente);

        } catch (Exception e){

            return ResponseEntity
                    .status(500)
                    .body("Error al editar la asignación: " + e.getMessage());
        }
    }
}