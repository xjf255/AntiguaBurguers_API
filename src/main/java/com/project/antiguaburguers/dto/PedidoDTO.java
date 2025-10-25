package com.project.antiguaburguers.dto;

import com.project.antiguaburguers.model.EstadoPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoDTO(
        String numPedido,
        LocalDateTime fecha,
        EstadoPedido estado,
        String clienteNombre,
        String clienteDpi,
        BigDecimal total
) {}
