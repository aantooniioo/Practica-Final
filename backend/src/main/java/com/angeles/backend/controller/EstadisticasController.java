package com.angeles.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estadisticas")
@CrossOrigin(origins = "*")
public class EstadisticasController {

    @GetMapping("/generar")
    public ResponseEntity<String> generarEstadisticas() {
        try {

            ProcessBuilder pb = new ProcessBuilder(
                    "C:/Users/aangeles/AppData/Local/Python/pythoncore-3.14-64/python.exe",
                    "C:/Users/aangeles/OneDrive - Future Space/Practicas_Antonio/Practica_Final/python/analisis.py"
            );

            pb.redirectErrorStream(true);
            pb.inheritIO(); // 🔥 ver errores en consola

            Process proceso = pb.start();

            int exitCode = proceso.waitFor();

            if (exitCode == 0) {
                return ResponseEntity.ok("Estadísticas generadas correctamente");
            } else {
                return ResponseEntity.status(500).body("Error al ejecutar Python");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error interno del servidor");
        }
    }
}