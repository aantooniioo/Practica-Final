package com.angeles.backend.controller;

import com.angeles.backend.entity.Empleado;
import com.angeles.backend.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @GetMapping
    public List<Empleado> obtenerTodos(){
        return empleadoRepository.findAll()
                .stream()
                .filter(emp -> emp.getFechaBaja() == null)
                .toList();
    }

    @PostMapping
    public Empleado crearEmpleado(@RequestBody Empleado empleado){
        return empleadoRepository.save(empleado);
    }

    @PutMapping("/baja/{id}")
    public void darDeBaja(@PathVariable Integer id){
        Empleado emp = empleadoRepository.findById(id).orElse(null);

        if(emp != null){
            emp.setFechaBaja(LocalDate.now());
            empleadoRepository.save(emp);
        }
    }
}