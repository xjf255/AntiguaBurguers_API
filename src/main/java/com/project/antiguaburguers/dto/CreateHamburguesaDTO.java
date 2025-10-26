package com.project.antiguaburguers.dto;

import java.math.BigDecimal;

public record CreateHamburguesaDTO(String nombre, BigDecimal costo, BigDecimal costoCombo, String img, boolean existencia) {
}
