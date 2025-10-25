package com.project.antiguaburguers.dto;

public record PedidoDetailDTO(
        String numPedido,
        String fecha,
        String estado,
        ClienteDTO cliente,
        String direccionEntrega,
        Boolean requiereDelivery,
        String total,
        java.util.List<DetallePedidoDTO> items
) {}
