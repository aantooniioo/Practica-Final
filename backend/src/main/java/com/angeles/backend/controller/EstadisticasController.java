package com.angeles.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estadisticas")
@CrossOrigin(origins = "*")
public class EstadisticasController {

    // Ejecuta el script de Python para generar las estadísticas
    @GetMapping("/generar")
    public ResponseEntity<String> generarEstadisticas() {

        try {

            // En Docker, python3 está en /usr/bin/python3
            ProcessBuilder pb = new ProcessBuilder(
                    "/usr/bin/python3",
                    "/app/src/main/resources/analisis.py"
            );

            // Redirige los errores al mismo flujo de salida
            pb.redirectErrorStream(true);

            // Muestra la salida del proceso en la consola de Java
            pb.inheritIO();

            // Inicia el proceso
            Process proceso = pb.start();

            // Espera a que termine la ejecución
            int exitCode = proceso.waitFor();

            // Comprueba si el script se ejecutó correctamente
            if (exitCode == 0) {
                return ResponseEntity.ok("Estadísticas generadas correctamente");
            } else {
                return ResponseEntity.status(500).body("Error al ejecutar el script de Python");
            }

        } catch (Exception e) {

            // Muestra el error en consola para depuración
            e.printStackTrace();

            return ResponseEntity.status(500).body("Error interno del servidor: " + e.getMessage());
        }
    }
}
