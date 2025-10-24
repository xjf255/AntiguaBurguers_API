package com.project.antiguaburguers.model;

import java.io.Serializable;
import java.util.Objects;

public class BebidaId implements Serializable {
    private String nombre;
    private String cantidad;

    public BebidaId() {}

    public BebidaId(String nombre, String cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof BebidaId)) return false;
        BebidaId that = (BebidaId) o;
        return Objects.equals(nombre, that.nombre) && Objects.equals(cantidad, that.cantidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, cantidad);
    }
}
