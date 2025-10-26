package com.project.antiguaburguers.dto;

import java.math.BigDecimal;

public record CreateCombo(String numCombo, String nombre, BigDecimal costo, String descripcion, String img, Boolean existencia) {
}
