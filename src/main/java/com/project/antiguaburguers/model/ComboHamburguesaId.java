package com.project.antiguaburguers.model;

import java.io.Serializable;
import java.util.Objects;

public class ComboHamburguesaId implements Serializable {
    private String combo;
    private String nombreHamburguesa;

    public ComboHamburguesaId() {}

    public ComboHamburguesaId(String num_combo, String nombre_hamburguesa) {
        this.combo = num_combo;
        this.nombreHamburguesa = nombre_hamburguesa;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof ComboHamburguesaId)) return false;
        ComboHamburguesaId that = (ComboHamburguesaId) o;
        return Objects.equals(combo, that.combo) && Objects.equals(nombreHamburguesa, that.nombreHamburguesa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(combo, nombreHamburguesa);
    }
}
