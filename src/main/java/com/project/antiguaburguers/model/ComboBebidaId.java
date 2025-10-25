package com.project.antiguaburguers.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ComboBebidaId implements java.io.Serializable {
    @Column(name = "num_combo", length = 6, nullable = false)
    private String numCombo;

    @Column(name = "nombre_bebida", length = 150, nullable = false)
    private String nombreBebida;

    @Column(name = "cantidad_bebida", length = 20, nullable = false)
    private String cantidadBebida;

    public ComboBebidaId() {}

    public ComboBebidaId(String numCombo, String nombreBebida, String cantidadBebida) {
        this.numCombo = numCombo;
        this.nombreBebida = nombreBebida;
        this.cantidadBebida = cantidadBebida;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComboBebidaId that)) return false;
        return Objects.equals(numCombo, that.numCombo)
                && Objects.equals(nombreBebida, that.nombreBebida)
                && Objects.equals(cantidadBebida, that.cantidadBebida);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numCombo, nombreBebida, cantidadBebida);
    }
}
