package com.project.antiguaburguers.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "combo")
public class Combo {
    @Id
    @Generated(GenerationTime.INSERT)
    @Column(insertable = false, updatable = false)
    private String num_combo;
    private String nombre;
    private Double costo;
    private String descripcion;
    private String img;
    private Boolean existencia = true;
    private LocalDateTime created_at;

    @OneToMany(mappedBy = "combo")
    private List<ComboHamburguesa> hamburguesas;

    @OneToMany(mappedBy = "combo")
    private List<Promocion> hamburguesasEnPromocion;

    @PrePersist
    public void onCreated() {
        this.created_at = LocalDateTime.now();
    }
}
