package com.angeles.backend.service;

import com.angeles.backend.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
public class TestConexion implements CommandLineRunner {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public void run(String... args) {
        System.out.println("Conectado: " + empleadoRepository.count());
    }
}
