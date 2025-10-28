package com.project.antiguaburguers.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PromocionDTO(
        String numPromocion,
        String numCombo,
        String img,
        BigDecimal descuento,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        String descripcion,
        BigDecimal costo,
        String diasDisponibles
) {}
