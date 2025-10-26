package com.project.antiguaburguers.dto;

import java.math.BigDecimal;

public record ItemCatalogoDTO(
        String tipo,        // HAMBURGUESA | BEBIDA | COMPLEMENTO | COMBO
        String nombre,
        BigDecimal precioLista,
        BigDecimal precioVigente, // para promo
        boolean activo
) {}

