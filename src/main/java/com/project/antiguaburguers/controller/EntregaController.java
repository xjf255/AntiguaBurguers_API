package com.project.antiguaburguers.controller;

import com.project.antiguaburguers.model.Entrega;
import com.project.antiguaburguers.service.EntregaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/entregas")
@SecurityRequirement(name = "bearerAuth")
public class EntregaController {

    private final EntregaService entregaService;

    public EntregaController(EntregaService entregaService) {
        this.entregaService = entregaService;
    }

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
