package com.project.antiguaburguers.controller;

import com.project.antiguaburguers.dto.CreatePedidoDTO;
import com.project.antiguaburguers.dto.PedidoDTO;
import com.project.antiguaburguers.dto.PedidoDetailDTO;
import com.project.antiguaburguers.service.PedidoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Pedidos", description = "API de gestion de pedidos")
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listarPedidos() {
        List<PedidoDTO> pedidos = pedidoService.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{numPedido}")
    public ResponseEntity<PedidoDetailDTO> obtenerPedido(@PathVariable String numPedido) {
        PedidoDetailDTO pedido = pedidoService.obtenerDetalle(numPedido);
        return ResponseEntity.ok(pedido);
    }

    @PostMapping
    public ResponseEntity<PedidoDetailDTO> crearPedido(@RequestBody CreatePedidoDTO dto) {
        PedidoDetailDTO nuevo = pedidoService.crearPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PatchMapping("/{numPedido}/estado")
    public ResponseEntity<PedidoDTO> actualizarEstado(
            @PathVariable String numPedido,
            @RequestParam String nuevoEstado
    ) {
        PedidoDTO actualizado = pedidoService.actualizarEstado(numPedido, nuevoEstado);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{numPedido}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable String numPedido) {
        pedidoService.eliminarPedido(numPedido);
        return ResponseEntity.noContent().build();
    }
}

