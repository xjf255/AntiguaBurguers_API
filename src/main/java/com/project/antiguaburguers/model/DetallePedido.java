package com.project.antiguaburguers.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "pedido_detalle")
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Integer idDetalle;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "num_pedido", referencedColumnName = "num_pedido", nullable = false)
    private Pedido pedido;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "num_combo", referencedColumnName = "num_combo")
    private Combo combo;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "hamburguesa", referencedColumnName = "nombre")
    private Hamburguesa hamburguesa;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumns({
            @JoinColumn(name = "bebida_nombre", referencedColumnName = "nombre"),
            @JoinColumn(name = "bebida_cantidad", referencedColumnName = "cantidad")
    })
    private Bebida bebida;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "complemento", referencedColumnName = "nombre")
    private Complemento complemento;
    @Column(nullable = false)
    private Integer cantidad;
    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;
    @Column(name = "subtotal", insertable = false, updatable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;
}
