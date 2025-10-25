package com.project.antiguaburguers.mapper;

import com.project.antiguaburguers.dto.CreateDetalleDTO;
import com.project.antiguaburguers.dto.CreatePedidoDTO;
import com.project.antiguaburguers.model.DetallePedido;
import com.project.antiguaburguers.model.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PedidoCreateMapper {

    // DTO -> Entidad (Pedido)
    @Mappings({
            @Mapping(target = "numPedido", ignore = true),
            @Mapping(target = "fecha", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "estado", ignore = true),
            @Mapping(target = "cliente", ignore = true),
            @Mapping(target = "direccionEntrega", source = "direccionEntrega"),
            @Mapping(target = "requiereDelivery", source = "requiereDelivery"),
            @Mapping(target = "total", ignore = true),
            @Mapping(target = "created_at", ignore = true),
            @Mapping(target = "updated_at", ignore = true),
            @Mapping(target = "detalles", ignore = true) // 👈 evita el error
    })
    Pedido toEntity(CreatePedidoDTO dto);

    // DTO -> Entidad (Detalle)  (relaciones y pedido se resuelven en el Service)
    @Mappings({
            @Mapping(target = "idDetalle", ignore = true),
            @Mapping(target = "pedido", ignore = true),
            @Mapping(target = "combo", ignore = true),
            @Mapping(target = "hamburguesa", ignore = true),
            @Mapping(target = "bebida", ignore = true),
            @Mapping(target = "complemento", ignore = true),
            @Mapping(target = "cantidad", source = "cantidad"),
            @Mapping(target = "precioUnitario", expression =
                    "java( (dto.precioUnitario()==null||dto.precioUnitario().isBlank()) ? null : new java.math.BigDecimal(dto.precioUnitario()) )"),
            @Mapping(target = "subtotal", ignore = true) // lo calcula Java o la BD
    })
    DetallePedido toEntity(CreateDetalleDTO dto);
}
