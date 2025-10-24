package com.project.antiguaburguers.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "entrega")
public class Entrega {
    @Id
    @Column(name = "num_pedido", length = 10)
    private String numPedido;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "num_pedido", referencedColumnName = "num_pedido")
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "dpi_repartidor", referencedColumnName = "dpi", nullable = true)
    private Repartidor repartidor;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "estado", referencedColumnName = "estado", nullable = true)
    private EstadoEntrega estado;

    @Column(name = "direccion_entrega", length = 400)
    private String direccionEntrega;

    @Generated(GenerationTime.INSERT)
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
