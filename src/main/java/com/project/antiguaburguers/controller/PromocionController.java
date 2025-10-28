package com.project.antiguaburguers.controller;

import com.project.antiguaburguers.dto.PromocionDTO;
import com.project.antiguaburguers.service.PromocionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/promociones")
public class PromocionController {

    private final PromocionService promocionService;

    public PromocionController(PromocionService promocionService) {
        this.promocionService = promocionService;
    }

    // 🔹 GET /api/promociones → todas las promociones
    @GetMapping
    public ResponseEntity<List<PromocionDTO>> listarTodas() {
        return ResponseEntity.ok(promocionService.listarTodas());
    }

    // 🔹 GET /api/promociones/vigentes → solo las activas hoy
    @GetMapping("/vigentes")
    public ResponseEntity<List<PromocionDTO>> listarVigentes() {
        return ResponseEntity.ok(promocionService.listarVigentes());
    }

    // POST /api/promociones
    @PostMapping
    public ResponseEntity<?> crearPromocion(@RequestBody PromocionDTO dto) {
        try {
            return ResponseEntity.ok(promocionService.crearPromocion(dto));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("error", "Error al crear promoción"));
        }
    }

    // PUT /api/promociones/{numPromocion}
    @PutMapping("/{numPromocion}")
    public ResponseEntity<?> actualizarPromocion(@PathVariable String numPromocion,
                                                 @RequestBody PromocionDTO dto) {
        try {
            return ResponseEntity.ok(promocionService.actualizarPromocion(numPromocion, dto));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }

    // DELETE /api/promociones/{numPromocion}
    @DeleteMapping("/{numPromocion}")
    public ResponseEntity<?> eliminarPromocion(@PathVariable String numPromocion) {
        try {
            promocionService.eliminarPromocion(numPromocion);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(409).body(Map.of("error", "No se puede eliminar la promoción"));
        }
    }
}
