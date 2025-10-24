package com.project.antiguaburguers.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "promocion")
public class Promocion {

    @Id
    @Column(name="num_promocion", length=6, insertable=false, updatable=false)
    private String numPromocion;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "num_combo", referencedColumnName = "num_combo", nullable = true)
    private Combo combo;

    @Column(name = "descuento", nullable = false, precision = 5, scale = 2)
    private BigDecimal descuento;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @PrePersist
    @PreUpdate
    private void validarRangoFechas() {
        if (fechaInicio != null && fechaFin != null && fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("fechaFin no puede ser anterior a fechaInicio");
        }
    }
}
