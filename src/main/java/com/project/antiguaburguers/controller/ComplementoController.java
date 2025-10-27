package com.project.antiguaburguers.controller;

import com.project.antiguaburguers.model.Complemento;
import com.project.antiguaburguers.service.ComplementoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/complementos")
@CrossOrigin(origins = "*") // opcional
public class ComplementoController {

    private final ComplementoService complementoService;

    public ComplementoController(ComplementoService complementoService) {
        this.complementoService = complementoService;
    }

    /* ========= LISTAR / CONSULTAR ========= */

    @GetMapping
    public ResponseEntity<List<Complemento>> listarTodos() {
        return ResponseEntity.ok(complementoService.findAll());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Complemento>> listarDisponibles() {
        var disponibles = complementoService.findAll()
                .stream()
                .filter(c -> Boolean.TRUE.equals(c.getExistencia()))
                .toList();
        return ResponseEntity.ok(disponibles);
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<Complemento> obtener(@PathVariable String nombre) {
        var comp = complementoService.findById(nombre);
        return (comp != null) ? ResponseEntity.ok(comp) : ResponseEntity.notFound().build();
    }

    /* ========= CREAR / ACTUALIZAR / ELIMINAR ========= */

    @PostMapping
    public ResponseEntity<Complemento> crear(@RequestBody Complemento complemento) {
        // si quieres evitar duplicados, primero valida existsById en el repo/servicio
        var creado = complementoService.save(complemento);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{nombre}")
    public ResponseEntity<Complemento> actualizar(
            @PathVariable String nombre,
            @RequestBody Complemento body
    ) {
        var existente = complementoService.findById(nombre);
        if (existente == null) return ResponseEntity.notFound().build();

        existente.setImg(body.getImg());
        existente.setCosto(body.getCosto());
        existente.setCostoCombo(body.getCostoCombo());
        existente.setExistencia(body.getExistencia());

        return ResponseEntity.ok(complementoService.save(existente));
    }

    @PatchMapping("/{nombre}/precio")
    public ResponseEntity<Complemento> cambiarPrecio(
            @PathVariable String nombre,
            @RequestParam BigDecimal costo
    ) {
        var existente = complementoService.findById(nombre);
        if (existente == null) return ResponseEntity.notFound().build();

        existente.setCosto(costo);
        return ResponseEntity.ok(complementoService.save(existente));
    }

    @PatchMapping("/{nombre}/existencia")
    public ResponseEntity<Complemento> cambiarExistencia(
            @PathVariable String nombre,
            @RequestParam boolean existencia
    ) {
        var existente = complementoService.findById(nombre);
        if (existente == null) return ResponseEntity.notFound().build();

        existente.setExistencia(existencia);
        return ResponseEntity.ok(complementoService.save(existente));
    }

    @DeleteMapping("/{nombre}")
    public ResponseEntity<Void> eliminar(@PathVariable String nombre) {
        var existente = complementoService.findById(nombre);
        if (existente == null) return ResponseEntity.notFound().build();

        complementoService.remove(nombre);
        return ResponseEntity.noContent().build();
    }
}
