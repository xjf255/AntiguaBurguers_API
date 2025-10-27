package com.project.antiguaburguers.mapper;

import com.project.antiguaburguers.dto.BebidaRefDTO;
import com.project.antiguaburguers.dto.ComboRefDTO;
import com.project.antiguaburguers.dto.ComplementoRefDTO;
import com.project.antiguaburguers.dto.HamburguesaRefDTO;
import com.project.antiguaburguers.model.Bebida;
import com.project.antiguaburguers.model.Combo;
import com.project.antiguaburguers.model.Complemento;
import com.project.antiguaburguers.model.Hamburguesa;
import org.mapstruct.Mapper;

@Mapper(config = CentralMapperConfig.class)
public interface RefMappers {

    default ComboRefDTO toRef(Combo combo) {
        return (combo == null) ? null : new ComboRefDTO(combo.getNumCombo());
    }

    default HamburguesaRefDTO toRef(Hamburguesa h) {
        return (h == null) ? null : new HamburguesaRefDTO(h.getNombre());
    }

    default ComplementoRefDTO toRef(Complemento c) {
        return (c == null) ? null : new ComplementoRefDTO(c.getNombre());
    }

    default BebidaRefDTO toRef(Bebida b) {
        if (b == null) return null;
        return new BebidaRefDTO(b.getNombre(), b.getCantidad());
    }
}

