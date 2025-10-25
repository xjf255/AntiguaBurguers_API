package com.project.antiguaburguers.mapper;

import com.project.antiguaburguers.dto.PedidoDTO;
import com.project.antiguaburguers.dto.PedidoDetailDTO;
import com.project.antiguaburguers.model.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(
        componentModel = "spring",
        uses = { MoneyMapper.class, ClienteMapper.class, DetallePedidoMapper.class }
)
public interface PedidoMapper {

    // Summary
    @Mappings({
            @Mapping(target = "numPedido", source = "numPedido"),
            @Mapping(target = "fecha",     source = "fecha"),
            @Mapping(target = "estado",    source = "estado.estado"),
            @Mapping(target = "clienteNombre",
                    expression = "java(p.getCliente() == null ? null : (p.getCliente().getNombre() + \" \" + p.getCliente().getApellido()))"),
            @Mapping(target = "clienteDpi", source = "cliente.dpi"),
            @Mapping(target = "total",      source = "total", qualifiedByName = "toMoney")
    })
    PedidoDTO toSummary(Pedido p);

    // Detail
    @Mappings({
            @Mapping(target = "numPedido",        source = "numPedido"),
            @Mapping(target = "fecha",            source = "fecha"),
            @Mapping(target = "estado",           source = "estado.estado"),
            @Mapping(target = "cliente",          source = "cliente"),
            @Mapping(target = "direccionEntrega", source = "direccionEntrega"),
            @Mapping(target = "requiereDelivery", source = "requiereDelivery"),
            @Mapping(target = "total",            source = "total", qualifiedByName = "toMoney"),
            @Mapping(target = "items",            source = "detalles")
    })
    PedidoDetailDTO toDetail(Pedido p);
}
