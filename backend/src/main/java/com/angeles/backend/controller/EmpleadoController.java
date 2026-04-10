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

    // Obtiene todos los empleados activos (sin fecha de baja)
    @GetMapping
    public List<Empleado> obtenerTodos(){
        return empleadoRepository.findByFechaBajaIsNull()
                .stream()
                .filter(emp -> emp.getFechaBaja() == null)
                .toList();
    }

    // Obtiene un empleado por su id
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id){

        Empleado emp = empleadoRepository.findById(id).orElse(null);

        if(emp == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(emp);
    }

    // Crea un nuevo empleado con validaciones básicas
    @PostMapping
    public ResponseEntity<?> crearEmpleado(@RequestBody Empleado empleado){

        // Validar nombre obligatorio
        if(empleado.getNombre() == null || empleado.getNombre().isEmpty()){
            return ResponseEntity.badRequest().body("El nombre es obligatorio");
        }

        // Validar primer apellido obligatorio
        if(empleado.getApellido1() == null || empleado.getApellido1().isEmpty()){
            return ResponseEntity.badRequest().body("El primer apellido es obligatorio");
        }

        // Validar email obligatorio
        if(empleado.getEmail() == null || empleado.getEmail().isEmpty()){
            return ResponseEntity.badRequest().body("El email es obligatorio");
        }

        return ResponseEntity.ok(empleadoRepository.save(empleado));
    }

    // Actualiza los datos de un empleado existente
    @PutMapping("/{id}")
    public ResponseEntity<?> editarEmpleado(@PathVariable Integer id, @RequestBody Empleado datos){

        Empleado emp = empleadoRepository.findById(id).orElse(null);

        if(emp == null){
            return ResponseEntity.notFound().build();
        }

        // Validar nombre obligatorio
        if(datos.getNombre() == null || datos.getNombre().isEmpty()){
            return ResponseEntity.badRequest().body("El nombre es obligatorio");
        }

        // Validar primer apellido obligatorio
        if(datos.getApellido1() == null || datos.getApellido1().isEmpty()){
            return ResponseEntity.badRequest().body("El primer apellido es obligatorio");
        }

        // Validar email obligatorio
        if(datos.getEmail() == null || datos.getEmail().isEmpty()){
            return ResponseEntity.badRequest().body("El email es obligatorio");
        }

        // Actualiza los campos del empleado
        emp.setNombre(datos.getNombre());
        emp.setApellido1(datos.getApellido1());
        emp.setApellido2(datos.getApellido2());
        emp.setEmail(datos.getEmail());
        emp.setTelefono1(datos.getTelefono1());
        emp.setTelefono2(datos.getTelefono2());
        emp.setEstadoCivil(datos.getEstadoCivil());
        emp.setFormacionUniversitaria(datos.getFormacionUniversitaria());

        empleadoRepository.save(emp);

        return ResponseEntity.ok(emp);
    }

    // Da de baja un empleado si no tiene asignaciones
    @PutMapping("/baja/{id}")
    public ResponseEntity<?> darDeBaja(@PathVariable Integer id){

        // Comprueba si el empleado tiene proyectos asignados
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