package com.project.antiguaburguers.controller;

import com.project.antiguaburguers.model.Bebida;
import com.project.antiguaburguers.model.BebidaId;
import com.project.antiguaburguers.repository.BebidaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/bebidas")
@CrossOrigin(origins = "*") // opcional si tu frontend está en otro dominio
public class BebidaController {

    private final BebidaRepository bebidaRepository;

    public BebidaController(BebidaRepository bebidaRepository) {
        this.bebidaRepository = bebidaRepository;
    }

    /* ======================================
       LISTAR / CONSULTAR / FILTRAR
       ====================================== */

    // Todas las bebidas
    @GetMapping
    public List<Bebida> listarTodas() {
        return bebidaRepository.findAll();
    }

    // Solo bebidas disponibles (existencia = true)
    @GetMapping("/disponibles")
    public List<Bebida> listarDisponibles() {
        return bebidaRepository.findByExistenciaTrue();
    }

    // Buscar por ID compuesto
    @GetMapping("/{nombre}/{cantidad}")
    public ResponseEntity<Bebida> obtenerPorId(@PathVariable String nombre, @PathVariable String cantidad) {
        var id = new BebidaId(nombre, cantidad);
        var bebida = bebidaRepository.findById(id);
        return bebida.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /* ======================================
       CREAR / ACTUALIZAR / ELIMINAR
       ====================================== */

    // Crear nueva bebida
    @PostMapping
    public ResponseEntity<Bebida> crearBebida(@RequestBody Bebida nueva) {
        // Evita crear duplicados exactos
        var id = new BebidaId(nueva.getNombre(), nueva.getCantidad());
        if (bebidaRepository.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        nueva.setExistencia(true);
        return ResponseEntity.ok(bebidaRepository.save(nueva));
    }

    // Actualizar bebida existente
    @PutMapping("/{nombre}/{cantidad}")
    public ResponseEntity<Bebida> actualizarBebida(
            @PathVariable String nombre,
            @PathVariable String cantidad,
            @RequestBody Bebida actualizada
    ) {
        var id = new BebidaId(nombre, cantidad);
        var existente = bebidaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bebida no encontrada: " + nombre + " " + cantidad));

        existente.setCosto(actualizada.getCosto());
        existente.setCostoCombo(actualizada.getCostoCombo());
        existente.setImg(actualizada.getImg());
        existente.setExistencia(actualizada.getExistencia());

        return ResponseEntity.ok(bebidaRepository.save(existente));
    }

    // Cambiar solo el precio
    @PatchMapping("/{nombre}/{cantidad}/precio")
    public ResponseEntity<Bebida> actualizarPrecio(
            @PathVariable String nombre,
            @PathVariable String cantidad,
            @RequestParam BigDecimal costo
    ) {
        var id = new BebidaId(nombre, cantidad);
        var bebida = bebidaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bebida no encontrada: " + nombre + " " + cantidad));
        bebida.setCosto(costo);
        return ResponseEntity.ok(bebidaRepository.save(bebida));
    }

    // Activar o desactivar (existencia)
    @PatchMapping("/{nombre}/{cantidad}/existencia")
    public ResponseEntity<Bebida> cambiarExistencia(
            @PathVariable String nombre,
            @PathVariable String cantidad,
            @RequestParam boolean existencia
    ) {
        var id = new BebidaId(nombre, cantidad);
        var bebida = bebidaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bebida no encontrada: " + nombre + " " + cantidad));
        bebida.setExistencia(existencia);
        return ResponseEntity.ok(bebidaRepository.save(bebida));
    }

    // Eliminar bebida
    @DeleteMapping("/{nombre}/{cantidad}")
    public ResponseEntity<Void> eliminarBebida(@PathVariable String nombre, @PathVariable String cantidad) {
        var id = new BebidaId(nombre, cantidad);
        if (!bebidaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bebidaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

