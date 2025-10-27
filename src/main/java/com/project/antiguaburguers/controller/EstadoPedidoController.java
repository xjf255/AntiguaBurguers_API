package com.project.antiguaburguers.controller;

import com.project.antiguaburguers.model.EstadoPedido;
import com.project.antiguaburguers.repository.EstadoPedidoRepository;
import com.project.antiguaburguers.utils.EstadoPedidoEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/estados-pedido")
@CrossOrigin(origins = "*")
public class EstadoPedidoController {

    private final EstadoPedidoRepository estadoPedidoRepository;

    public EstadoPedidoController(EstadoPedidoRepository estadoPedidoRepository) {
        this.estadoPedidoRepository = estadoPedidoRepository;
    }

    @GetMapping
    public ResponseEntity<List<EstadoPedido>> listarEstados() {
        return ResponseEntity.ok(estadoPedidoRepository.findAll());
    }

    @GetMapping("/enum")
    public ResponseEntity<List<EstadoPedidoEnum>> listarEnum() {
        return ResponseEntity.ok(Arrays.asList(EstadoPedidoEnum.values()));
    }
}
