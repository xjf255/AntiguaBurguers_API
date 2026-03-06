package com.project.antiguaburguers.controller;

import com.project.antiguaburguers.model.Repartidor;
import com.project.antiguaburguers.service.RepartidorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/repartidores")
public class RepartidorController {

    private final RepartidorService repartidorService;

    public RepartidorController(RepartidorService repartidorService) {
        this.repartidorService = repartidorService;
    }

    @GetMapping
    public ResponseEntity<List<Repartidor>> listarTodos() {
        return ResponseEntity.ok(repartidorService.findAll());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Repartidor>> listarDisponibles() {
        var disponibles = repartidorService.findAll()
                .stream()
                .filter(Repartidor::isEstado)
                .toList();
        return ResponseEntity.ok(disponibles);
    }

    @GetMapping("/{dpi}")
    public ResponseEntity<Repartidor> obtenerPorDpi(@PathVariable String dpi) {
        var repartidor = repartidorService.findById(dpi);
        return (repartidor != null)
                ? ResponseEntity.ok(repartidor)
                : ResponseEntity.notFound().build();
    }

    // Crear nuevo repartidor
    @PostMapping
    public ResponseEntity<Repartidor> crear(@RequestBody Repartidor repartidor) {
        Repartidor creado = repartidorService.saveRepartidor(repartidor);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // Actualizar repartidor existente
    @PutMapping("/{dpi}")
    public ResponseEntity<Repartidor> actualizar(
            @PathVariable String dpi,
            @RequestBody Repartidor datosActualizados
    ) {
        Repartidor existente = repartidorService.findById(dpi);
        if (existente == null)
            return ResponseEntity.notFound().build();

        existente.setNombre(datosActualizados.getNombre());
        existente.setApellido(datosActualizados.getApellido());
        existente.setPrefijo_telefono(datosActualizados.getPrefijo_telefono());
        existente.setTelefono(datosActualizados.getTelefono());
        existente.setEmail(datosActualizados.getEmail());
        existente.setEstado(datosActualizados.isEstado());

        return ResponseEntity.ok(repartidorService.saveRepartidor(existente));
    }

    // Cambiar disponibilidad
    @PatchMapping("/{dpi}/estado")
    public ResponseEntity<Repartidor> cambiarEstado(
            @PathVariable String dpi,
            @RequestParam boolean estado
    ) {
        Repartidor existente = repartidorService.findById(dpi);
        if (existente == null)
            return ResponseEntity.notFound().build();

        existente.setEstado(estado);
        return ResponseEntity.ok(repartidorService.saveRepartidor(existente));
    }

    // Eliminar repartidor
    @DeleteMapping("/{dpi}")
    public ResponseEntity<Void> eliminar(@PathVariable String dpi) {
        Repartidor existente = repartidorService.findById(dpi);
        if (existente == null)
            return ResponseEntity.notFound().build();

        repartidorService.deleteRepartidor(existente);
        return ResponseEntity.noContent().build();
    }
}
