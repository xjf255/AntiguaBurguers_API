package com.project.antiguaburguers.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.generator.EventType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "combo")
public class Combo {
    @Id
    @Column(name = "num_combo", insertable = false, updatable = false, length = 6)
    private String numCombo;
    private String nombre;
    private BigDecimal costo;
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
