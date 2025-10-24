package com.project.antiguaburguers.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "bebida")
@IdClass(BebidaId.class)
public class Bebida {
    @Id
    @Column(length = 150, nullable = false)
    private String nombre;
    @Id
    @Column(length = 20, nullable = false)
    private String cantidad;
    @Column(nullable = false)
    private Double costo;
    private Double costo_combo;
    private String img;
    private Boolean existencia = true;
    private LocalDateTime created_at;

    @PrePersist
    public void onCreate() {
        created_at = LocalDateTime.now();
    }
}
