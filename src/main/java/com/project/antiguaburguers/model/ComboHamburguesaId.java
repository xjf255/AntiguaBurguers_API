package com.project.antiguaburguers.model;

import java.io.Serializable;
import java.util.Objects;

public class ComboHamburguesaId implements Serializable {
    private String num_combo;
    private String nombre_hamburguesa;

    public ComboHamburguesaId() {}

    public ComboHamburguesaId(String num_combo, String nombre_hamburguesa) {
        this.num_combo = num_combo;
        this.nombre_hamburguesa = nombre_hamburguesa;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof ComboHamburguesaId)) return false;
        ComboHamburguesaId that = (ComboHamburguesaId) o;
        return Objects.equals(num_combo, that.num_combo) && Objects.equals(nombre_hamburguesa, that.nombre_hamburguesa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(num_combo, nombre_hamburguesa);
    }
}
