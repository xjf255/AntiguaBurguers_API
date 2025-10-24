package com.project.antiguaburguers.model;

import com.project.antiguaburguers.utils.EstadoEntregaEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "estado_entrega")
public class EstadoEntrega {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 50)
    private EstadoEntregaEnum estado;
}
