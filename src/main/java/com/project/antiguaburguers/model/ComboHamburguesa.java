package com.project.antiguaburguers.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name ="combo_hamburguesa")
@IdClass(ComboHamburguesaId.class)
public class ComboHamburguesa {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "num_combo", nullable = false)
    private Combo num_combo;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nombre_hamburguesa", nullable = false)
    private Hamburguesa nombre_hamburguesa;
}
