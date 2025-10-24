package com.project.antiguaburguers.model;


import com.project.antiguaburguers.utils.EstadoPedidoEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "estado_pedido")
public class EstadoPedido {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EstadoPedidoEnum estado;
}
