package com.project.antiguaburguers.dto;

public record CreateDetalleDTO(
        Integer cantidad,
        String precioUnitario,
        String numCombo,                // opcional
        String hamburguesa,             // opcional
        String bebidaNombre,            // opcional
        String bebidaCantidad,          // opcional
        String complemento              // opcional
) {}