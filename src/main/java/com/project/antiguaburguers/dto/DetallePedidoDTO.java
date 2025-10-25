package com.project.antiguaburguers.dto;

public record DetallePedidoDTO(
        Integer idDetalle,
        Integer cantidad,
        String precioUnitario,
        String subtotal,
        ComboRefDTO combo,
        HamburguesaRefDTO hamburguesa,
        BebidaRefDTO bebida,
        ComplementoRefDTO complemento
) {}
