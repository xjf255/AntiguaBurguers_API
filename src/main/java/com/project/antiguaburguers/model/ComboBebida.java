package com.project.antiguaburguers.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "combo_bebida")
public class ComboBebida {

    @EmbeddedId
    private ComboBebidaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "num_combo", referencedColumnName = "num_combo",
            insertable = false, updatable = false)
    private Combo combo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "nombre_bebida",   referencedColumnName = "nombre",   insertable = false, updatable = false),
            @JoinColumn(name = "cantidad_bebida", referencedColumnName = "cantidad", insertable = false, updatable = false)
    })
    private Bebida bebida;
}
