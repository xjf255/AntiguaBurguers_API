package com.project.antiguaburguers.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "bebida")
@IdClass(BebidaId.class)
public class Bebida {
    @Id
    @Column(name = "nombre", length = 150)
    private String nombre;
    @Id
    @Column(name = "cantidad", length = 20)
    private String cantidad;
    @Column(name = "costo", nullable = false, precision = 10, scale = 2)
    private BigDecimal costo;
    @Column(name = "costo_combo", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal costoCombo;
    @Column(name = "img", length = 500)
    private String img;
    @Column(name = "existencia")
    private Boolean existencia = true;
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}