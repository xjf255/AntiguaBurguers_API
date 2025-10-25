package com.project.antiguaburguers.mapper;

import com.project.antiguaburguers.dto.DetallePedidoDTO;
import com.project.antiguaburguers.model.DetallePedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = { MoneyMapper.class, RefMappers.class })
public interface DetallePedidoMapper {

    @Mappings({
            @Mapping(target = "idDetalle", source = "idDetalle"),
            @Mapping(target = "cantidad",  source = "cantidad"),
            @Mapping(target = "precioUnitario", source = "precioUnitario", qualifiedByName = "toMoney"),
            @Mapping(target = "subtotal",       source = "subtotal",       qualifiedByName = "toMoney"),
            @Mapping(target = "combo",       expression = "java(RefMappers.superToComboRef(entity.getCombo()))", ignore = true) // si ya tienes RefMappers, usa tus métodos allí
    })
    DetallePedidoDTO toDTO(DetallePedido entity);
}
