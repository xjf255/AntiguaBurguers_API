package com.project.antiguaburguers.dto;

public record CreatePedidoDTO(
        String dpiCliente,
        String direccionEntrega,
        Boolean requiereDelivery,
        java.util.List<CreateDetalleDTO> items
) {}
