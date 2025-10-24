package com.project.antiguaburguers.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ComboBebidaId implements Serializable {
    @Column(name = "num_combo", length = 6, nullable = false)
    private String num_combo;
    private BebidaId bebidaId;

    public ComboBebidaId() {}

    public ComboBebidaId(String num_combo, BebidaId bebidaId) {
        this.num_combo = num_combo;
        this.bebidaId = bebidaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComboBebidaId that)) return false;
        return Objects.equals(num_combo, that.num_combo)
                && Objects.equals(bebidaId, that.bebidaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(num_combo, bebidaId);
    }
}
