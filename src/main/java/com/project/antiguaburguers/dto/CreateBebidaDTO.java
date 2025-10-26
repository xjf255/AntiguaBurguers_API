package com.project.antiguaburguers.dto;

import java.math.BigDecimal;

public record CreateBebidaDTO(String nombre, String cantidad, BigDecimal costo, BigDecimal costoCombo, String img, Boolean existencia) {
}
