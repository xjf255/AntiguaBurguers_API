package com.project.antiguaburguers.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "hamburguesa")
public class Hamburguesa {
    @Id
    private String nombre;
    @Column(nullable = false)
    private double costo;
    private double costo_combo;
    private String img;
    private boolean existencia = true;
    private LocalDateTime created_at;

    @PrePersist
    public void onCreate() {
        created_at = LocalDateTime.now();
    }
}
