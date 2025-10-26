package com.project.antiguaburguers.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "complemento")
public class Complemento {
    @Id
    @Column(length = 150)
    private String nombre;
    private String img;
    @Column(nullable = false)
    private BigDecimal costo;
    private BigDecimal costoCombo;
    private Boolean existencia = true;
    private LocalDateTime created_at;

    @PrePersist
    public void onCreated() {
        created_at = LocalDateTime.now();
    }
}
