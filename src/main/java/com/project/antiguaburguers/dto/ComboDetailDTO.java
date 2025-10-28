package com.project.antiguaburguers.dto;

import java.math.BigDecimal;
import java.util.List;

public record ComboDetailDTO(
        String numCombo,
        String nombre,
        String descripcion,
        String img,
        BigDecimal costo,
        Boolean existencia,
        List<String> hamburguesas,
        List<String> bebidas,
        List<String> complementos
) {}
