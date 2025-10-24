package com.project.antiguaburguers.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "combo_complemento")
@IdClass(ComboComplementoId.class)
public class ComboComplemento {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "num_combo", referencedColumnName = "num_combo", nullable = false)
    private Combo combo;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nombre_complemento", referencedColumnName = "nombre", nullable = false)
    private Complemento complemento;
}
