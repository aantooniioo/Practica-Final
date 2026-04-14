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

    @GetMapping
    public List<Empleado> obtenerTodos(){
        return empleadoRepository.findByFechaBajaIsNull()
                .stream()
                .filter(emp -> emp.getFechaBaja() == null)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id){

        Empleado emp = empleadoRepository.findById(id).orElse(null);

        if(emp == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(emp);
    }

    @PostMapping
    public ResponseEntity<?> crearEmpleado(@RequestBody Empleado empleado){

        try {

            if(empleado.getNombre() == null || empleado.getNombre().isEmpty()){
                return ResponseEntity.badRequest().body("El nombre es obligatorio");
            }

            if(empleado.getApellido1() == null || empleado.getApellido1().isEmpty()){
                return ResponseEntity.badRequest().body("El primer apellido es obligatorio");
            }

            if(empleado.getEmail() == null || empleado.getEmail().isEmpty()){
                return ResponseEntity.badRequest().body("El email es obligatorio");
            }

            // VALORES POR DEFECTO

            if(empleado.getApellido2() == null){
                empleado.setApellido2("");
            }

            if(empleado.getTelefono1() == null){
                empleado.setTelefono1("000000000");
            }

            if(empleado.getTelefono2() == null){
                empleado.setTelefono2("000000000");
            }

            if(empleado.getEstadoCivil() == null){
                empleado.setEstadoCivil("S");
            }

            if(empleado.getFormacionUniversitaria() == null){
                empleado.setFormacionUniversitaria("N");
            }

            if(empleado.getFechaAlta() == null){
                empleado.setFechaAlta(LocalDate.now());
            }

            if(empleado.getFechaNacimiento() == null){
                empleado.setFechaNacimiento(LocalDate.of(2000, 1, 1));
            }

            /**
             * Genera automáticamente el email corporativo evitando duplicados
             */
            String base =
                    empleado.getNombre().substring(0,1).toLowerCase() +
                            empleado.getApellido1().substring(0,1).toLowerCase() +
                            empleado.getApellido2().toLowerCase();

            String emailGenerado = base + "@futurespace.es";

            int contador = 1;

            // Comprueba si el email ya existe y añade numeración si es necesario
            while(empleadoRepository.existsByEmail(emailGenerado)){
                emailGenerado = base + contador + "@futurespace.es";
                contador++;
            }

            // Asigna el email generado al empleado
            empleado.setEmail(emailGenerado);

            Empleado nuevo = empleadoRepository.save(empleado);

            return ResponseEntity.ok(nuevo);

        } catch (Exception e) {

            return ResponseEntity
                    .status(500)
                    .body("Error al crear empleado: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarEmpleado(@PathVariable Integer id, @RequestBody Empleado datos){

        Empleado emp = empleadoRepository.findById(id).orElse(null);

        if(emp == null){
            return ResponseEntity.notFound().build();
        }

        if(datos.getNombre() == null || datos.getNombre().isEmpty()){
            return ResponseEntity.badRequest().body("El nombre es obligatorio");
        }

        if(datos.getApellido1() == null || datos.getApellido1().isEmpty()){
            return ResponseEntity.badRequest().body("El primer apellido es obligatorio");
        }

        if(datos.getEmail() == null || datos.getEmail().isEmpty()){
            return ResponseEntity.badRequest().body("El email es obligatorio");
        }

        emp.setNombre(datos.getNombre());
        emp.setApellido1(datos.getApellido1());
        emp.setApellido2(datos.getApellido2());
        emp.setNif(datos.getNif());
        emp.setEmail(datos.getEmail());
        emp.setTelefono1(datos.getTelefono1());
        emp.setTelefono2(datos.getTelefono2());
        emp.setFechaNacimiento(datos.getFechaNacimiento());
        emp.setEstadoCivil(datos.getEstadoCivil());
        emp.setFormacionUniversitaria(datos.getFormacionUniversitaria());

        empleadoRepository.save(emp);

        return ResponseEntity.ok(emp);
    }

    @PutMapping("/baja/{id}")
    public ResponseEntity<?> darDeBaja(@PathVariable Integer id){

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
