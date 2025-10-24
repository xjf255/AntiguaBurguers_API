package com.project.antiguaburguers.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "combo_bebida")
@IdClass(BebidaId.class)
public class ComboBebida {

    @EmbeddedId
    private ComboBebidaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("num_combo")
    @JoinColumn(name = "num_combo", referencedColumnName = "num_combo", nullable = false)
    private Combo combo;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bebidaId")
    @JoinColumn(name = "nombre_bebida", referencedColumnName = "nombre", nullable = false)
    @JoinColumn(name = "cantidad_bebida", referencedColumnName = "cantidad", nullable = false)
    private Bebida bebida;
}
