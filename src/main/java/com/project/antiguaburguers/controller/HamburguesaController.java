package com.project.antiguaburguers.controller;

import com.project.antiguaburguers.model.Hamburguesa;
import com.project.antiguaburguers.service.HamburguesaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/hamburguesas")
@CrossOrigin(origins = "*")
public class HamburguesaController {

    private final HamburguesaService hamburguesaService;

    public HamburguesaController(HamburguesaService hamburguesaService) {
        this.hamburguesaService = hamburguesaService;
    }

    @GetMapping
    public ResponseEntity<List<Hamburguesa>> listarTodas() {
        return ResponseEntity.ok(hamburguesaService.getAllHamburguesas());
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<Hamburguesa> obtenerPorNombre(@PathVariable String nombre) {
        var hamburguesa = hamburguesaService.getHamburguesa(nombre);
        return hamburguesa != null
                ? ResponseEntity.ok(hamburguesa)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Hamburguesa>> listarDisponibles() {
        var disponibles = hamburguesaService.getAllHamburguesas()
                .stream()
                .filter(Hamburguesa::isExistencia)
                .toList();
        return ResponseEntity.ok(disponibles);
    }

    @PostMapping
    public ResponseEntity<Hamburguesa> crear(@RequestBody Hamburguesa hamburguesa) {
        Hamburguesa creada = hamburguesaService.saveHamburguesa(hamburguesa);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{nombre}")
    public ResponseEntity<Hamburguesa> actualizar(
            @PathVariable String nombre,
            @RequestBody Hamburguesa datosActualizados
    ) {
        Hamburguesa existente = hamburguesaService.getHamburguesa(nombre);
        if (existente == null)
            return ResponseEntity.notFound().build();

        existente.setCosto(datosActualizados.getCosto());
        existente.setCostoCombo(datosActualizados.getCostoCombo());
        existente.setImg(datosActualizados.getImg());
        existente.setExistencia(datosActualizados.isExistencia());

        Hamburguesa guardada = hamburguesaService.saveHamburguesa(existente);
        return ResponseEntity.ok(guardada);
    }

    @PatchMapping("/{nombre}/precio")
    public ResponseEntity<Hamburguesa> cambiarPrecio(
            @PathVariable String nombre,
            @RequestParam BigDecimal costo
    ) {
        Hamburguesa existente = hamburguesaService.getHamburguesa(nombre);
        if (existente == null)
            return ResponseEntity.notFound().build();

        existente.setCosto(costo);
        return ResponseEntity.ok(hamburguesaService.saveHamburguesa(existente));
    }

    @PatchMapping("/{nombre}/existencia")
    public ResponseEntity<Hamburguesa> cambiarExistencia(
            @PathVariable String nombre,
            @RequestParam boolean existencia
    ) {
        Hamburguesa existente = hamburguesaService.getHamburguesa(nombre);
        if (existente == null)
            return ResponseEntity.notFound().build();

        existente.setExistencia(existencia);
        return ResponseEntity.ok(hamburguesaService.saveHamburguesa(existente));
    }

    @DeleteMapping("/{nombre}")
    public ResponseEntity<Void> eliminar(@PathVariable String nombre) {
        Hamburguesa existente = hamburguesaService.getHamburguesa(nombre);
        if (existente == null)
            return ResponseEntity.notFound().build();

        hamburguesaService.deleteHamburguesa(nombre);
        return ResponseEntity.noContent().build();
    }
}
