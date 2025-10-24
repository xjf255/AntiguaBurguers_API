package com.project.antiguaburguers.model;

import java.io.Serializable;
import java.util.Objects;

public class ComboComplementoId implements Serializable {
    private String combo;
    private String complemento;

    public ComboComplementoId() {}

    public ComboComplementoId(String combo, String complemento) {
        this.combo = combo;
        this.complemento = complemento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComboComplementoId that)) return false;
        return Objects.equals(combo, that.combo)
                && Objects.equals(complemento, that.complemento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(combo, complemento);
    }
}
