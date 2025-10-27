package com.project.antiguaburguers.controller;

import com.project.antiguaburguers.model.Entrega;
import com.project.antiguaburguers.service.EntregaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/entregas")
@CrossOrigin(origins = "*")
public class EntregaController {

    private final EntregaService entregaService;

    public EntregaController(EntregaService entregaService) {
        this.entregaService = entregaService;
    }

    /* ======================================================
       ASIGNAR REPARTIDOR Y MARCAR EN_RUTA
       ====================================================== */
    @PatchMapping("/{numPedido}/asignar/{dpiRepartidor}")
    public ResponseEntity<?> asignarRepartidor(
            @PathVariable String numPedido,
            @PathVariable String dpiRepartidor
    ) {
        try {
            Entrega entrega = entregaService.asignarRepartidor(numPedido, dpiRepartidor);
            return ResponseEntity.ok(entrega);
        } catch (EntityNotFoundException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /* ======================================================
       MARCAR ENTREGA COMO ENTREGADA
       ====================================================== */
    @PatchMapping("/{numPedido}/entregada")
    public ResponseEntity<?> marcarEntregada(@PathVariable String numPedido) {
        try {
            Entrega entrega = entregaService.marcarEntregada(numPedido);
            return ResponseEntity.ok(entrega);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
