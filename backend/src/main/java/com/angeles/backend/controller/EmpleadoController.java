package com.angeles.backend.controller;

import com.angeles.backend.entity.Empleado;
import com.angeles.backend.repository.EmpleadoRepository;
import com.angeles.backend.repository.EmpleadoProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private EmpleadoProyectoRepository empleadoProyectoRepository;

    // Obtener empleados activos
    @GetMapping
    public List<Empleado> obtenerTodos(){
        return empleadoRepository.findAll()
                .stream()
                .filter(emp -> emp.getFechaBaja() == null)
                .toList();
    }

    // Crear empleado con validaciones básicas
    @PostMapping
    public ResponseEntity<?> crearEmpleado(@RequestBody Empleado empleado){

        // Validar campos obligatorios
        if(empleado.getNombre() == null || empleado.getNombre().isEmpty()){
            return ResponseEntity.badRequest().body("El nombre es obligatorio");
        }

        if(empleado.getApellido1() == null || empleado.getApellido1().isEmpty()){
            return ResponseEntity.badRequest().body("El primer apellido es obligatorio");
        }

        if(empleado.getEmail() == null || empleado.getEmail().isEmpty()){
            return ResponseEntity.badRequest().body("El email es obligatorio");
        }

        return ResponseEntity.ok(empleadoRepository.save(empleado));
    }

    // Dar de baja con restricción de asignaciones
    @PutMapping("/baja/{id}")
    public ResponseEntity<?> darDeBaja(@PathVariable Integer id){

        // Comprobar si tiene proyectos asignados
        int asignaciones = empleadoProyectoRepository.countByIdEmpleado(id);

        if(asignaciones > 0){
            return ResponseEntity
                    .badRequest()
                    .body("No se puede dar de baja el empleado porque está asignado a proyectos");
        }

        Empleado emp = empleadoRepository.findById(id).orElse(null);

        if(emp != null){
            emp.setFechaBaja(LocalDate.now());
            empleadoRepository.save(emp);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}