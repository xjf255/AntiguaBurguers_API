package com.project.antiguaburguers.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "pedido")
public class Pedido {
    @Id
    @Column(name = "num_pedido", length = 10)
    private String numPedido;

    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estado", referencedColumnName = "estado", nullable = false)
    private EstadoPedido estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dpi_cliente", referencedColumnName = "dpi", nullable = false)
    private Cliente cliente;

    @Column(name = "direccion_entrega", length = 400)
    private String direccionEntrega;

    @Column(name = "requiere_delivery")
    private Boolean requiereDelivery = false;

    @Column(precision = 12, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @OneToMany(
            mappedBy = "pedido",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<DetallePedido> detalles = new java.util.ArrayList<>();

    @PrePersist
    public void onCreate() {
        created_at = LocalDateTime.now();
        if (fecha == null) fecha = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updated_at = LocalDateTime.now();
    }
}
