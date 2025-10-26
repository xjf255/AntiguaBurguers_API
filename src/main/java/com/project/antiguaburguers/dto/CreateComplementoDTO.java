package com.project.antiguaburguers.dto;

import java.math.BigDecimal;

public record CreateComplementoDTO(String nombre, String img, BigDecimal costo, BigDecimal costoCombo, Boolean existencia ) {
}
